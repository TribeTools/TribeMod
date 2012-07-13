package net.minecraft.FranticMod.Gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.FranticMod.Util.ArrayLib;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class GuiManualPage extends GuiScreen {
	private final String[] lines;
	GuiScreen parent;

	int scroll = 0;

	public GuiManualPage(GuiScreen parent, String title, String[] lines) {
		this.lines = ArrayLib.concat(new String[] { "[C]\2477" + title, "" }, lines);
		this.parent = parent;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void keyTyped(char c, int i) {
		if (i == 1)
        {
            mc.displayGuiScreen(parent);
        }

		if (i == Keyboard.KEY_UP)
			scroll = Math.max(0, scroll - 1);
		if (i == Keyboard.KEY_DOWN)
			scroll = Math.min(lines.length - 1, scroll + 1);
	}

	@Override
	public void drawScreen(int i, int j, float f) {
        if(mc.theWorld != null)
        {
            drawGradientRect(10, 0, width - 10, height, 0xc0101010, 0xd0101010);
        } else
        {
            drawBackground(i);
        }

		for (int c = 0; c < lines.length; c++) {
			String line;
			try {
				line = lines[c + scroll];
			} catch (ArrayIndexOutOfBoundsException e) {
				line = "";
			}

			if (line.startsWith("[C]")) {
				line = line.substring(3);
				fontRenderer.drawString(line, (width / 2) - (fontRenderer.getStringWidth(line) / 2), c * 10 + 10, 0xffffff);
			} else {
				fontRenderer.drawString(line, 15, c * 10 + 10, 0xffffff);
			}

		}

		super.drawScreen(i, j, f);
	}
	
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0) scroll = Math.max(0, scroll - 1);
			if (i < 0) scroll = Math.min(lines.length - 1, scroll + 1);
		}
	}
}
