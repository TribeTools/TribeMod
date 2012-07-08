package hiimcody1.Util;

import net.minecraft.src.ModLoader;
import hiimcody1.FranticMod;

public class VanishNoPacketParser {
	private String chatMessage;
	private boolean vanished;
	private boolean shouldCancel=false;
	
	public VanishNoPacketParser(String chatMessage) {
		this.chatMessage = chatMessage;
		vanished = FranticMod.isVanished;
		parseMessage();
	}
	
	public static void performVanishCheck() {
		if(!Boolean.parseBoolean(Config.props.getProperty("showVanishInChat")))
		ModLoader.getMinecraftInstance().thePlayer.sendChatMessage("/vanish check");
	}
	/*
	 * Messages:
	 * 
	 * You have joined vanished. To appear: /vanish
	 * 
	 * You have become visible.
	 * 
	 * You have vanished. Poof.
	 * 
	 * You are invisible.
	 * 
	 * You are not invisible.
	 * 
	 * Red - Already invisible :)
	 * 
	 * Red - Already visible :)
	 */
	private void parseMessage() {
		if(chatMessage.startsWith(ChatColors.Teal+"You have joined vanished.") ||
		   chatMessage.startsWith(ChatColors.Teal+"You have vanished")	||
		   chatMessage.startsWith(ChatColors.Teal+"You are invisible") ||
		   chatMessage.startsWith(ChatColors.Red+"Already invisible")) {
			vanished = true;
			shouldCancel=!Boolean.parseBoolean(Config.props.getProperty("showVanishInChat"));
		}
		if(chatMessage.startsWith(ChatColors.Teal+"You have become visible") ||
		   chatMessage.startsWith(ChatColors.Teal+"You are not invisible")	||
		   chatMessage.startsWith(ChatColors.Red+"Already visible")) {
			vanished = false;
			shouldCancel=!Boolean.parseBoolean(Config.props.getProperty("showVanishInChat"));
		}
	}
	
	public boolean shouldCancel() {
		return shouldCancel;
	}
	
	public boolean isVanished() {
		return vanished;
	}
}
