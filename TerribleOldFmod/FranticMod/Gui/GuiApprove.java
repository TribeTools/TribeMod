// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.FranticMod.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Util.ChatColors;
import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StatCollector, GuiSlotStatsGeneral, GuiSlotStatsItem, 
//            GuiSlotStatsBlock, StringTranslate, GuiButton, GuiSlot, 
//            RenderHelper, Item, RenderItem, RenderEngine, 
//            Tessellator, StatFileWriter, FontRenderer

public class GuiApprove extends GuiScreen
{
	
	public static int selected;

    public GuiApprove(GuiScreen guiscreen)
    {
        title = "Approve Members";
        selectedSlot = null;
        parentGui = guiscreen;
    }

    @Override
	public void initGui()
    {
    	title = "Approve Members";
        slotGeneral = new GuiSlotApprove(this);
        slotGeneral.registerScrollButtons(controlList, 1, 1);
        selectedSlot = slotGeneral;
        addHeaderButtons();
    }

    public void addHeaderButtons()
    {
        controlList.add(new GuiButton(0, width / 2 + 4, height - 28, 150, 20, "Back"));
        controlList.add(new GuiButton(1, width / 8 + 4, height - 28, 150, 20, "Approve"));
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
        	FranticMod.getLogger().info(mod_FranticMod.approveList.get(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected]).toString());
        	if(mod_FranticMod.playerIsOnline(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected])) {
        		try {
					UrlLib.ApproveMember(mod_FranticMod.approveList.get(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected]).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else {
        		mod_FranticMod.showModMessage("FMod - Not Ingame", GuiSlotApprove.results.keySet().toArray(new String[] {})[selected], Block.bedrock.blockID);
        	}
        	//UrlLib.ApproveMember(AUserId);
            mc.displayGuiScreen(null);
        }
    }

    @Override
	public void drawScreen(int i, int j, float f)
    {
        selectedSlot.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, title, width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }


    protected GuiScreen parentGui;
    protected String title;
    private GuiSlotApprove slotGeneral;
    private GuiSlot selectedSlot;

}
