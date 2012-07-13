package net.minecraft.FranticMod.Event;

import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Util.ChatColors;
import net.minecraft.FranticMod.Util.Config;
import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.FranticMod.Util.WorldGuardParser;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_FranticMod;

public class ChatEvent extends Event {
	private FranticMod controller;
	private String message;
	private Direction direction;
	
	
	public ChatEvent(FranticMod controller,String message,Direction direction) {
		this.controller = controller;
		this.message=message;
		this.direction=direction;
	}
	
	@Override
	public void execute() {
		if(this.direction==Direction.INCOMING) {
				FranticMod.getLogger().info(message);
				WorldGuardParser wg = new WorldGuardParser(message);
				if(wg.isWorldGuard())
				{
					this.isCancelled=true;
					mod_FranticMod.showModMessage("WorldGuard - "+wg.getAction(), wg.getPlayer(), wg.getItem());
				}
				if(message.startsWith(ChatColors.Teal)) {
					if(message.endsWith("You have vanished. Poof.")) {
						mod_FranticMod.isVisible = false;
						this.isCancelled=true;
					}
					if(message.endsWith("You have become visible.")) {
						mod_FranticMod.isVisible = true;
						this.isCancelled=true;
					}
					if(message.endsWith("You are not invisible.")) {
						mod_FranticMod.isVisible = true;
						this.isCancelled=true;
					}
					if(message.endsWith("You are invisible.")) {
						mod_FranticMod.isVisible = false;
						this.isCancelled=true;
					}
				}
				//this.isCancelled=mod_FranticMod.hideChat;
				//mod_FranticMod.showMessage(message);
			//controller.debug("Incoming chat");
		} else if (this.direction==Direction.OUTGOING) {
			if(controller.props.getProperty("confirmUpdate") != null) {
				if(message.equals("/yes")) {
					UrlLib.download_zip_file(mod_FranticMod.newVersion);
				} else if (message.equals("/no")) {
					mod_FranticMod.AppData.remove("confirmUpdate");
				}
			}
			if(controller.props.getProperty("pendingMsg") != null) {
				if(message.equals("/yes")) {
					ModLoader.getMinecraftInstance().thePlayer.sendChatMessage(Config.props.getProperty("pendingMsg"));
					controller.props.remove("pendingMsg");
					this.isCancelled = true;
				}
				if(message.equals("/no")) {
					mod_FranticMod.chatMessage(ChatColors.Yellow+"Message Cancelled. I just saved you getting fail spam.");
					controller.props.remove("pendingMsg");
					this.isCancelled = true;
				}
			}
			if(message.startsWith("t/") || message.startsWith("t/") || message.startsWith("tp ") || message.startsWith("r ")) {
				mod_FranticMod.chatMessage(ChatColors.Yellow+"Error Protection: Are you sure you want to send '"+ChatColors.Gray+message+ChatColors.Yellow+"'???");
				mod_FranticMod.chatMessage(ChatColors.Yellow+"/yes or /no");
				controller.props.setProperty("pendingMsg", message);
				this.isCancelled = true;
			}
			//if(message.startsWith("//") && message.length() > 2) {
			//	mod_FranticMod.chatMessage(message);
			//	this.isCancelled=true;
			//}
			//controller.debug("Outgoing chat");
		}
	}
	
	public static enum Direction {

	    INCOMING,
	    OUTGOING;

	    public String toString() {
	        return name().toLowerCase();
	    }
	}
}

