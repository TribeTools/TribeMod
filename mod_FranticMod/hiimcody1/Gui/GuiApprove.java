// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package hiimcody1.Gui;

import hiimcody1.Util.GuiTools;
import hiimcody1.Util.PlayerTools;
import hiimcody1.Util.UrlLib;

import java.util.HashMap;


import net.minecraft.src.*;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StatCollector, GuiSlotStatsGeneral, GuiSlotStatsItem, 
//            GuiSlotStatsBlock, StringTranslate, GuiButton, GuiSlot, 
//            RenderHelper, Item, RenderItem, RenderEngine, 
//            Tessellator, StatFileWriter, FontRenderer

public class GuiApprove extends GuiScreen
{
	
	public static int selected;
	
	public static HashMap<String, Object> approveList = new HashMap<String, Object>();
	
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
        controlList.add(new GuiButton(0, width / 2 + 80, height - 28, 100, 20, "Back"));
        controlList.add(new GuiButton(1, width / 12 + 4, height - 28, 100, 20, "Approve"));
        controlList.add(new GuiButton(2, width / 3 + 25, height - 28, 100, 20, "Deny"));
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
        	if(PlayerTools.playerIsOnline(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected])) {
        		try {
					UrlLib.ApproveMember(GuiApprove.approveList.get(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected]).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else {
        		GuiTools.showModMessage("FMod - Not Ingame", GuiSlotApprove.results.keySet().toArray(new String[] {})[selected], Block.bedrock.blockID);
        	}
        	//UrlLib.ApproveMember(AUserId);
            mc.displayGuiScreen(null);
        }
        if(guibutton.id == 2)
        {
        	if(!PlayerTools.playerIsOnline(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected])) {
        		try {
					UrlLib.DenyMember(GuiApprove.approveList.get(GuiSlotApprove.results.keySet().toArray(new String[] {})[selected]).toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else {
        		GuiTools.showModMessage("FMod - Ingame", GuiSlotApprove.results.keySet().toArray(new String[] {})[selected], Block.bedrock.blockID);
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

    
    @Override
    public void updateScreen() {
    	super.updateScreen();
    }
    
    protected GuiScreen parentGui;
    protected String title;
    private GuiSlotApprove slotGeneral;
    private GuiSlot selectedSlot;

}
