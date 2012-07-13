package net.minecraft.FranticMod.Commands;

import net.minecraft.FranticMod.Gui.GuiConsole;


public abstract class Command {
	public abstract void handleCommand(String[] args, GuiConsole con);
	
	public String[] helpText = { "There is no help" };
}
