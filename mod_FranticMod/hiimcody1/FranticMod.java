package hiimcody1;

import java.io.IOException;
import java.lang.reflect.Field;

import hiimcody1.Gui.GuiConsole;
import hiimcody1.Gui.GuiFModMenu;
import hiimcody1.Gui.GuiLogin;
import hiimcody1.Gui.GuiUserSearch;
import hiimcody1.Gui.GuiWGLog;
import hiimcody1.Overrides.DataPacketList;
import hiimcody1.Overrides.Packet3FModChat;
import hiimcody1.Session.Temp;
import hiimcody1.Session.WorldGuard;
import hiimcody1.Util.ChatColors;
import hiimcody1.Util.Config;
import hiimcody1.Util.DateUtils;
import hiimcody1.Util.GuiTools;
import hiimcody1.Util.LogBlockParser;
import hiimcody1.Util.McCompat;
import hiimcody1.Util.ModLoaderCompat;
import hiimcody1.Util.StringTools;
import hiimcody1.Util.UrlLib;
import hiimcody1.Util.VanishNoPacketParser;
import hiimcody1.Util.WorldGuardParser;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiControls;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Session;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.mod_FranticMod;

@SuppressWarnings("unused")
public class FranticMod {
	public static boolean pendingConfirm = false;

	public Minecraft mc = ModLoader.getMinecraftInstance();
	private mod_FranticMod fMod;
	private String fModVer;
	private long tm = System.currentTimeMillis();
	private boolean registered = false;

	public static boolean isVanished;
	public static ModLoaderCompat fw = new ModLoaderCompat();
	
	public static McCompat mcTools = new McCompat();
	
	private KeyBinding keyBindOpenMenu = new KeyBinding("key.fmodmenu", Keyboard.KEY_BACK);
	//private KeyBinding keyBindConsole = new KeyBinding("key.console", Keyboard.KEY_INSERT);
	//private KeyBinding keyBindWGConsole = new KeyBinding("key.wgconsole", Keyboard.KEY_GRAVE);
	// private KeyBinding keyBindChat = new KeyBinding("key.chat",
	// Keyboard.KEY_T);

	private boolean parsingLb = false;

	public FranticMod(mod_FranticMod mod) {
		this.fMod = mod;
	}

	public void Initialize() {
		ChatColors.init();
		fModVer = fMod.getVersion();
		UrlLib.cacheBanList();
		try {
			Config.loadConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!Config.props.containsKey("showVanishMessage")) {
			Config.props.setProperty("showVanishMessage", String.valueOf(false));
		}
		if(!Config.props.containsKey("showWGSpam")) {
			Config.props.setProperty("showWGSpam", String.valueOf(false));
		}
		
		try {
			Config.saveConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fw.mapKey(fMod, keyBindOpenMenu, false);
		//fw.mapKey(fMod, keyBindConsole, false);
		//fw.mapKey(fMod, keyBindWGConsole, false);

		fw.addLocalization("key.fmodmenu", "Open FMod Menu");

		Packet3FModChat.register(this);
		
	}

	public void processTick(float tickNumber, Minecraft callerInstance) {
		//Draw FPS
		if(!mc.gameSettings.showDebugInfo)
		GuiTools.drawString(mc.debug, 2, 2, 0xFFFFFF);
		
		//System.out.println(mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.thePlayer, mc.thePlayer.boundingBox));
		
		if (!registered) {
			DataPacketList.register(this);
			registered = true;
			if (Boolean.parseBoolean(Config.props.getProperty("autologin"))) {
				try {
					UrlLib.validateCredentials(Config.props.getProperty("username"), Config.props.getProperty("password"));
				} catch (Exception e) {
					mc.thePlayer.sendChatMessage("/m Console Send this to Cody :::: FMod Exception: " + e.getMessage());
				}
			}
		}

		if (isVanished) {
			ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			GuiTools.drawOutlineString("Vanished", (scaledresolution.getScaledWidth() - mc.fontRenderer.getStringWidth("Vanished")) / 2, 2, 0xffff00, 0x555500);
		}

		if (System.currentTimeMillis() - tm > 5 * 1000) { // Every 5 seconds,
														  // check
			tm = System.currentTimeMillis();
			VanishNoPacketParser.performVanishCheck();
		}
	}
	
	public void processGuiTick(float f, Minecraft minecraft, GuiScreen guiscreen) {

	}
	
	public void processKeybind(KeyBinding event) {
		if (event == this.keyBindOpenMenu && GuiTools.isIdle()) {
			if (Temp.loggedIn) {
				fw.showScreen(mc.thePlayer, new GuiFModMenu());
			} else {
				fw.showScreen(mc.thePlayer, new GuiLogin(mc.currentScreen));
			}
		}
	}

	public boolean processIncomingMessage(String message) {
		boolean cancelled = false;
		VanishNoPacketParser vp = new VanishNoPacketParser(message); // Done
		isVanished = vp.isVanished();
		cancelled = vp.shouldCancel();
		WorldGuardParser wg = new WorldGuardParser(message);
		if (wg.isWorldGuard()) {
			GuiTools.showModMessage("WorldGuard - " + wg.getAction(), wg.getPlayer(), wg.getItem());
			String itemName = (new ItemStack(wg.getItem(), 0, 0).getItem()).getItemName();
			WorldGuard.rows++;
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("[" + (WorldGuard.rows) + "] ");
			stringBuilder.append(DateUtils.now());
			stringBuilder.append(" - ");
			stringBuilder.append(wg.getPlayer());
			stringBuilder.append(" ");
			stringBuilder.append(wg.getAction());
			stringBuilder.append(" ");
			stringBuilder.append(itemName);
			GuiWGLog.addLine(stringBuilder.toString());
			WorldGuard.putWgData((WorldGuard.rows), wg);
			cancelled = !Boolean.parseBoolean(Config.props.getProperty("showWGInChat"));
		}
		LogBlockParser lb = new LogBlockParser(message);
		if (lb.isLogBlock() || parsingLb) {
			// Nothing for now
		}

		return cancelled;
	}

	public boolean processOutgoingMessage(String message) {
		boolean cancelled = false;

		// Check if we need to confirm something
		if (pendingConfirm && ((message.equals("/yes") || message.equals("/no")))) {
			boolean doIt = message.equals("/yes");
			processConfirmMessage(doIt);
			cancelled = true;
			pendingConfirm = false;
			return true;
		}
		
		if(message.startsWith("/fmodhelp")) {
			//irc.sendMessage("#franticme", message.substring(3));
			GuiTools.localMessage(ChatColors.DarkGreen+"FranticMod "+this.fModVer);
			GuiTools.localMessage(ChatColors.DarkGreen+"List of available commands: ");
			GuiTools.localMessage(ChatColors.DarkGreen+"/fmodhelp - Shows this message");
			GuiTools.localMessage(ChatColors.DarkGreen+"/irc - send a message to IRC");
			GuiTools.localMessage(ChatColors.DarkGreen+"/i - shortcut for /irc");
			GuiTools.localMessage(ChatColors.DarkGreen+"/irccommand  - send a command to IRC");
			GuiTools.localMessage(ChatColors.DarkGreen+"/focus irc - Routes ALL chat to IRC, minus /commands");
			GuiTools.localMessage(ChatColors.DarkGreen+"/focus mc - Routes chat back to mc");
			GuiTools.localMessage(ChatColors.DarkGreen+"/unfocus irc - alias of /focus mc");
			//GuiTools.localMessage(ChatColors.DarkGreen+"!help - view available ! commands (mostly for irc)");
			cancelled = true;
		}
		
		// Check for fuckups
			if ((message.startsWith(".") && !message.startsWith("./")) || message.startsWith("a ") || message.startsWith("r ") || message.startsWith("m ") || message.startsWith("tp ")) {
				cancelled = true;
				StringTools.storeStringWithConfirm(message);
			}
		return cancelled;
	}

	public void processConfirmMessage(boolean doIt) {
		if (doIt) {
			// Multiple options will be here, but not yet.
			GuiTools.localMessage(ChatColors.Gray + "Sending message...");
			mc.thePlayer.sendChatMessage(Temp.stringData.get("confirm"));
		} else {
			GuiTools.localMessage(ChatColors.Gray + "Sending cancelled.");
		}
		Temp.stringData.remove("confirm");
	}

}
