package net.minecraft.FranticMod;

import java.util.HashMap;
import java.util.Properties;

import net.minecraft.FranticMod.Commands.CmdMan;
import net.minecraft.FranticMod.Commands.Command;
import net.minecraft.FranticMod.Event.Event;
import net.minecraft.FranticMod.Gui.GuiConsole;
import net.minecraft.FranticMod.Overrides.Packet3FMChat;
import net.minecraft.FranticMod.Util.Logging;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_FranticMod;

public class FranticMod {
	private static Logging logger = new Logging();
	public Properties props = mod_FranticMod.AppData;
	public static HashMap<Object, Object> tempData = mod_FranticMod.tempData;
	
	public static HashMap<String, Command> cmdMap = new HashMap<String, Command>();
	
	public static Logging getLogger() {
		return logger;
	}
	
	public void initialize() {
		Packet3FMChat.register(this);
		cmdMap.put("man", new CmdMan());
	}
	
	public void callEvent(Event event) {
		event.execute();
	}
	
	public void debug(String message) {
		logger.info(message);
	}
	
	public void consoleCommand(String command, GuiConsole con) {
		String[] commandSplit = command.split(" ");
		if(commandSplit.length > 0) {
			if(commandSplit[0] == "rei") {
				ModLoader.getMinecraftInstance().ingameGUI.addChatMessage("\2470\2470\2471\2472\2473\2474\2475\2476\2477\247e\247f");
			}
		}
		
		for (String x : cmdMap.keySet()) {
			if (x.equalsIgnoreCase(commandSplit[0])) {
				cmdMap.get(x).handleCommand(commandSplit, con);
			}
		}
	}
}
