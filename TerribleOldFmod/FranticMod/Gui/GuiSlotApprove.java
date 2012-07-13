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
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;

// Referenced classes of package net.minecraft.src:
//            GuiSlot, GuiStats, StatList, StatBase, 
//            StatFileWriter, FontRenderer, Tessellator

public class GuiSlotApprove extends GuiSlot
{

    final GuiApprove parent; /* synthetic field */
    static Minecraft mc = ModLoader.getMinecraftInstance();

    public GuiSlotApprove(GuiApprove guiapprove)
    {
    	super(mc, guiapprove.width, guiapprove.height, 32, guiapprove.height - 64, 10);
    	results = new ConcurrentHashMap<String, String>();
        parent = guiapprove;
        func_27258_a(false);
        try {
        	getApprove();
        } catch(Exception r) {}
    }
    
    protected void getApprove() throws IOException
    {
    	try {
			UrlLib.ListApprove();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
    	GuiApprove.selected = i;
    }

    @Override
	protected boolean isSelected(int i)
    {
        return i == GuiApprove.selected;
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
