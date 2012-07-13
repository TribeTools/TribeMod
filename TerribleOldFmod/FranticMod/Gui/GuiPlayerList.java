// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.FranticMod.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StatCollector, GuiSlotStatsGeneral, GuiSlotStatsItem, 
//            GuiSlotStatsBlock, StringTranslate, GuiButton, GuiSlot, 
//            RenderHelper, Item, RenderItem, RenderEngine, 
//            Tessellator, StatFileWriter, FontRenderer

public class GuiPlayerList extends GuiScreen
{
	
	public static int selected = 0;
	private String searchQuery;

    public GuiPlayerList(GuiScreen guiscreen, String AQuery)
    {
        title = "";
        selectedSlot = null;
        parentGui = guiscreen;
        searchQuery = AQuery;
    }

    public void updateScreen() {
    	((GuiButton)controlList.get(1)).enabled = slotGeneral.getSize() > 0;
    }
    
    @Override
	public void initGui()
    {
    	title = "Search Results";
        slotGeneral = new GuiSlotPlayer(this,searchQuery);
        slotGeneral.registerScrollButtons(controlList, 1, 1);
        selectedSlot = slotGeneral;
        addHeaderButtons();
    }

    public void addHeaderButtons()
    {
        controlList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, "Back"));
        controlList.add(new GuiButton(1, width / 8 + 4, height - 28, 150, 20, "Select"));
    }

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
        	if(slotGeneral.results.keySet().toArray(new String[] {})[selected] != null)
        		mc.displayGuiScreen(new GuiPlayerInfo(slotGeneral.results.keySet().toArray(new String[] {})[selected]));
        	else
        		mod_FranticMod.showModMessage("FMod - Error", "You must select a name", Block.tnt.blockID);
        	
        }
    }

    @Override
	public void drawScreen(int i, int j, float f)
    {
        selectedSlot.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, "Search Results (" + slotGeneral.getSize() + ")", width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }


    protected GuiScreen parentGui;
    protected String title;
    private GuiSlotPlayer slotGeneral;
    private GuiSlot selectedSlot;

}
