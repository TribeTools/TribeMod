package net.minecraft.FranticMod.Commands;

import net.minecraft.FranticMod.Gui.GuiConsole;
import net.minecraft.FranticMod.Gui.GuiManualPage;
import net.minecraft.src.ModLoader;

public class CmdMan extends Command {

	@Override
	public void handleCommand(String[] args, GuiConsole con) {
		GuiConsole.addLine("Displaying man page: " + args[1] + "...");
		ModLoader.getMinecraftInstance().displayGuiScreen(new GuiManualPage(con, args[1], new String[] {
				"test1", "test2", args[1]
		}));
	}

}
