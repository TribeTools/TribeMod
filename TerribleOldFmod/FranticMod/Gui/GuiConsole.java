// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode
// Stuff to bind:
// drop
package net.minecraft.FranticMod.Gui;

import java.util.ArrayList;

import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.mod_FranticMod;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, EntityPlayerSP, GuiIngame, ChatAllowedCharacters
public class GuiConsole extends GuiScreen {

	public static ArrayList<String> lines = new ArrayList<String>();
	public static ArrayList<String> hist  = new ArrayList<String>();
	
    public GuiConsole() {
        message = "";
        updateCounter = 0;
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
	public void updateScreen() {
        updateCounter++;
    }

    int j;
    @Override
	protected void keyTyped(char c, int i) {
        if (i == 1) {
            mc.displayGuiScreen(null);
            return;
        } else
        if (i == 28) {
            String s = message.trim();
            if (s.length() > 0) {
                String s1 = message.trim();
                handleCommand(s1);
                message = "";
                j = 0;
                hist.add(message);
            }
            return;
        } else
        if (i == Keyboard.KEY_BACK && message.length() >= 0) {
            message = message.substring(0, message.length() - 1);
        } else 
        if (ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0) {
        	message += c;
        } else
        if (i == Keyboard.KEY_UP) {
        	message = hist.get(Math.min(hist.size(), j++));
        }
    }

    private void handleCommand(String s) {
    	mod_FranticMod.controller.consoleCommand(s, this);
	}
    
    static int scroll;
    
	@Override
	public void drawScreen(int i, int j, float f) {
		if(mc.theWorld != null)
        {
            drawGradientRect(10, 0, width - 10, height, 0xc0101010, 0xd0101010);
        } else
        {
            drawBackground(0);
        }

		for (int c = 0; c < lines.size() && c < 21; c++) {
			String line;
			try {
				line = lines.get(c + scroll);
			} catch (IndexOutOfBoundsException e) {
				line = "";
			}

			if (line.startsWith("[C]")) {
				line = line.substring(3);
				fontRenderer.drawString(line, (width / 2) - (fontRenderer.getStringWidth(line) / 2), c * 10 + 10, 0xffffff);
			} else {
				fontRenderer.drawString(line, 15, c * 10 + 10, 0xffffff);
			}

		}
		
		fontRenderer.drawString("> " + message + ((updateCounter / 6) % 2 != 0 ? "" : "_"), 11, height - 10, 0xffffff);
        super.drawScreen(i, j, f);
    }

    protected String message;
    private int updateCounter;
	
    @Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();
		if (i != 0) {
			if (i > 0) scroll = Math.max(0, scroll - 1);
			if (i < 0) scroll = Math.min(lines.size() - 21, scroll + 1);
		}
	}
    
    public static void addLine(String line) {
    	lines.add(line);
    	if (lines.size() > 21) {
    		scroll++;
    	}
    }
    
}
