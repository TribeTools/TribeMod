package net.minecraft.FranticMod.Util;

import java.util.List;

import net.minecraft.src.GuiButton;

public class GuiTools {
	public static GuiButton getButtonByDisplayString(List<?> controllist, String displayString) {
		for (int i = 0; i < controllist.size(); i++) {
			if(controllist.get(i) instanceof GuiButton) {
				if(((GuiButton) controllist.get(i)).displayString.equalsIgnoreCase(displayString))
					return ((GuiButton) controllist.get(i));
			}
		}
		return null;
	}
}
