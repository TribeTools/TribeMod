// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.FranticMod.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;

// Referenced classes of package net.minecraft.src:
//            GuiSlot, GuiStats, StatList, StatBase, 
//            StatFileWriter, FontRenderer, Tessellator

public class GuiSlotPlayer extends GuiSlot
{

    final GuiScreen parent; /* synthetic field */
    static Minecraft mc = ModLoader.getMinecraftInstance();

    public GuiSlotPlayer(GuiPlayerList guiplayerlist, String searchQuery)
    {
    	super(mc, guiplayerlist.width, guiplayerlist.height, 32, guiplayerlist.height - 64, 10);
    	results = new ConcurrentHashMap<String, String>();
        parent = guiplayerlist;
        func_27258_a(false);
        try {
        	getSearchResults(searchQuery);
        } catch(Exception r) {}
    }
    
    public GuiSlotPlayer(GuiBabyVote guibabyvote)
    {
    	super(mc, guibabyvote.width, guibabyvote.height, 32, guibabyvote.height - 64, 10);
    	results = new ConcurrentHashMap<String, String>();
        parent = guibabyvote;
        func_27258_a(false);
        try {
        	getBabyMods();
        } catch(Exception r) {}
    }
    
    protected void getSearchResults(String AQuery) throws Exception
    {
    	UrlLib.SearchUser(AQuery);
    	//results.put("improv32", "Moderator");
    	//results.put("imprvot22", "Regular");
    	//results.put("impwfwrov32", "Moderfweator");
    	//results.put("impfewfrvot22", "Regfewfular");

    }
    
    protected void getBabyMods() throws Exception
    {
    	UrlLib.RetrieveBabies();
    	//results.put("improv32", "Moderator");
    	//results.put("imprvot22", "Regular");
    	//results.put("impwfwrov32", "Moderfweator");
    	//results.put("impfewfrvot22", "Regfewfular");

    }

    @Override
	protected int getSize()
    {
        return results.size();
//    	return 1;
    }

    @Override
	protected void elementClicked(int i, boolean flag)
    {
    	GuiPlayerList.selected = i;
    }

    @Override
	protected boolean isSelected(int i)
    {
        return i == GuiPlayerList.selected;
    }

    @Override
	protected int getContentHeight()
    {
        return getSize() * 10;
    }

    @Override
	protected void drawBackground()
    {
        parent.drawDefaultBackground();
    }
    
    public static ConcurrentHashMap<String, String> results;

    @Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
    	ConcurrentHashMap<String, String> localResults = results;
    	String name = localResults.keySet().toArray(new String[] {})[i];
    	name = isSelected(i) ? "> " + name : name;
//    	String name = "pie";
        mc.fontRenderer.drawString(name, j + 2, k + 1, isSelected(i) ? 0x00ff00 : 0xffffff);
    	
        String rank = localResults.get(localResults.keySet().toArray(new String[] {})[i]);
        rank = isSelected(i) ? rank + " <" : rank;
//        String pass = "tits";
        mc.fontRenderer.drawString(rank, (j + 2 + 213) - mc.fontRenderer.getStringWidth(rank), k + 1, isSelected(i) ? 0x00ff00 : 0xffffff);
    }
}
