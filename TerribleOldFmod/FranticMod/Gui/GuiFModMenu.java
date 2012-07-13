// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.FranticMod.Gui;

import net.minecraft.FranticMod.Util.GuiTools;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiAchievements;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiStats;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.StatList;
import net.minecraft.src.mod_FranticMod;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiButton, StatCollector, GuiOptions, 
//            StatList, StatFileWriter, World, GuiMainMenu, 
//            GuiAchievements, GuiStats, MathHelper
@SuppressWarnings({ "unchecked", "unused" })
public class GuiFModMenu extends GuiScreen
{

	private int updateCounter2;
    private int updateCounter;

    public GuiFModMenu()
    {
        updateCounter2 = 0;
        updateCounter = 0;
    }

    
	public void initGui()
    {
        updateCounter2 = 0;
        controlList.clear();
        byte byte0 = -16;
        
        controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + byte0, "Lookup"));
        controlList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + byte0, 98, 20, "Approve"));
        
        controlList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + byte0, 98, 20, "Report Ban"));
        
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + byte0, "Config"));
        
        if(mod_FranticMod.rank >= 8)
        controlList.add(new GuiButton(7, width / 2 - 100, height / 4 + 72 + byte0, "Baby-Mod Review")); //Disabled due to API failing
    }

    protected void actionPerformed(GuiButton guibutton)
    {
    	if(guibutton.id == 0)
    	{
    		mc.displayGuiScreen(new GuiConfig(this));
    	}
        if(guibutton.id == 4)
        {
        	mc.displayGuiScreen(new GuiPlayerLookup(this));
        }
        if(guibutton.id == 5)
        { 
        	mc.displayGuiScreen(new GuiApprove(this));
        }
        if(guibutton.id == 6)
        {
        	mc.displayGuiScreen(new GuiReportBan(this));
        }
        if(guibutton.id == 7)
        {
        	mc.displayGuiScreen(new GuiBabyVote(this));
        }
    }

    public void updateScreen()
    {
        super.updateScreen();
        updateCounter++;
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        //if(GuiTools.getButtonByDisplayString(controlList, "Smite") != null) {
        //	GuiTools.getButtonByDisplayString(controlList, "Smite").enabled = false;
        //}
        //if(((GuiButton) controlList.get(3)).displayString.contains("Smite")) {
        //	((GuiButton) controlList.get(3)).enabled = false;
        //	((GuiButton) controlList.get(3)).displayString = ((updateCounter % 2 != 0) ? "\247c" : ((updateCounter % 3 != 0)) ? "\247a" : "\2479") + "Smite";
        //}
        //((GuiButton) controlList.get(1)).enabled = false;
        ((GuiButton) controlList.get(2)).enabled = false;
        
        drawCenteredString(fontRenderer, new Integer(height).toString(), width / 2, 40, 0xffffff);
        super.drawScreen(i, j, f);
    }
}
