// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode
// Stuff to bind:
// drop
package hiimcody1.Gui;

import hiimcody1.FranticMod;
import hiimcody1.Session.Temp;
import hiimcody1.Session.WorldGuard;
import hiimcody1.Util.ChatColors;
import hiimcody1.Util.GuiTools;
import hiimcody1.Util.Numbers;
import hiimcody1.Util.UrlLib;
import hiimcody1.Util.WorldGuardParser;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import net.minecraft.src.Block;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_FranticMod;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, EntityPlayerSP, GuiIngame, ChatAllowedCharacters
public class GuiUserHistory extends GuiScreen {

	public static ArrayList<String> lines = new ArrayList<String>();
	public static ArrayList<String> hist  = new ArrayList<String>();
	private int histInt = 0;
	
	
    public GuiUserHistory() {
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
    @Override
	protected void keyTyped(char c, int i) {
        if (i == 1) {
            mc.displayGuiScreen(null);
            return;
        } else
        if (i == Keyboard.KEY_RETURN) {
            String s = message.trim();
            if (s.length() > 2) {
                String s1 = message.trim();
                hist.add(message);
                try {
					UrlLib.getBanList(s1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					GuiTools.showModMessage("Error", "Search failed!", Block.tnt.blockID);
				}
                message = "";
                histInt = hist.size();
            }
            return;
        } else
        if (i == Keyboard.KEY_BACK && message.length() >= 1) {
            message = message.substring(0, message.length() - 1);
        } else 
        if (ChatAllowedCharacters.allowedCharacters.indexOf(c) >= 0) {
        	message += c;
        } else
        if (i == Keyboard.KEY_UP) {
        	histInt--;
        	try {
				message = hist.get(histInt);
			} catch (Exception e) {
				histInt++;
			}
        }
        if (i == Keyboard.KEY_DOWN) {
        	histInt++;
        	try {
				message = hist.get(histInt);
			} catch (Exception e) {
				histInt=hist.size();
				message="";
			}
        }
        if(i == 201) { //Keyboard Page Up
        	scroll = Math.max(0, scroll - 1);
        }
        if(i == 209) { //Keyboard Page Down
        	if(lines.size()>21)
        	scroll = Math.min(lines.size() - 21, scroll + 1);
        }
        //System.out.println("Keypress: "+i);
        //201 up
        //209 down
    }

    private void handleCommand(String s) {
    	
    	//mod_FranticMod.controller.consoleCommand(s, this);
	}
    
    static int scroll;
    
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public void drawScreen(int i, int j, float f) {
		if(mc.theWorld != null)
        {
            drawGradientRect(10, 0, width - 10, height, 0xc0101010, 0xd0101010);
        } else
        {
            drawBackground(0);
        }
		
		/*
		if(Temp.hashData.get("searchResults") != null) {
			List<String[]> results = (List<String[]>) Temp.hashData.get("searchResults");
			for(int l=0; l<results.size(); l++) {
				addLine(results.get(l)[0]);
			}
			Temp.hashData.remove("searchResults");
		}
		*/
		
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
		
		fontRenderer.drawString("["+histInt+"] Enter search string: " + message + ((updateCounter / 6) % 2 != 0 ? "" : "_"), 11, height - 10, 0xffffff);
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
			if(lines.size()>21)
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
