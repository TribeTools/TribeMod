// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.FranticMod.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import net.minecraft.FranticMod.FranticMod;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StatCollector, GuiSlotStatsGeneral, GuiSlotStatsItem, 
//            GuiSlotStatsBlock, StringTranslate, GuiButton, GuiSlot, 
//            RenderHelper, Item, RenderItem, RenderEngine, 
//            Tessellator, StatFileWriter, FontRenderer

@SuppressWarnings("unused")
public class GuiBabyVote extends GuiScreen
{
	
	public static int selected;

    public GuiBabyVote(GuiScreen guiscreen)
    {
        title = "";
        selectedSlot = null;
        parentGui = guiscreen;
    }

    public void updateScreen() {
    	((GuiButton)controlList.get(1)).enabled = slotGeneral.getSize() > 0;
    }
    
    @Override
	public void initGui()
    {
    	title = "Baby-Mod Review";
        slotGeneral = new GuiSlotBaby(this);
        slotGeneral.registerScrollButtons(controlList, 1, 1);
        selectedSlot = slotGeneral;
        addHeaderButtons();
    }

    @SuppressWarnings("unchecked")
	public void addHeaderButtons()
    {
        controlList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, "Back"));
        controlList.add(new GuiButton(1, width / 8 + 4, height - 28, 150, 20, "Select"));
    }

    @SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 0)
        {
            mc.displayGuiScreen(parentGui);
        }
        if(guibutton.id == 1)
        {
        	mc.displayGuiScreen(new GuiBabyInfo(slotGeneral.results.keySet().toArray(new String[] {})[selected]));
            System.out.println(((HashMap) FranticMod.tempData.get("babyData")).get(slotGeneral.results.keySet().toArray(new String[] {})[selected]));
        }
    }

    @Override
	public void drawScreen(int i, int j, float f)
    {
        selectedSlot.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, "Pending submissions (" + slotGeneral.getSize() + ")", width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }


    protected GuiScreen parentGui;
    protected String title;
    private GuiSlotBaby slotGeneral;
    private GuiSlot selectedSlot;

}
