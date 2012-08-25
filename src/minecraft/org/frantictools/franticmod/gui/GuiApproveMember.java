package org.frantictools.franticmod.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Tessellator;

public class GuiApproveMember extends GuiScreen
{
	public int selected;
	
	public static HashMap<String, Object> approveList = new HashMap<String, Object>();
	
    public GuiApproveMember(GuiScreen parent)
    {
        title = "Approve Members";
        playerList = null;
        this.parent = parent;
    }

    @Override
	public void initGui()
    {
    	playerList = new GuiSlotApprove(this);
        playerList.registerScrollButtons(controlList, 1, 1);
        addHeaderButtons();
    }

    public void addHeaderButtons()
    {
        controlList.add(new GuiButton(0, width / 2 + 80, height - 28, 100, 20, "« Back"));
        controlList.add(new GuiButton(1, width / 12 + 4, height - 28, 100, 20, "  \247aApprove"));
        controlList.add(new GuiButton(2, width / 3 + 25, height - 28, 100, 20, "  \247cDeny"));
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
            mc.displayGuiScreen(parent);
        }
        if(guibutton.id == 1)
        {
        	if (playerIsOnline(playerList.results.keySet().toArray(new String[] {})[selected].toString()))
        	{
        		// Approve
        	} else
        	{
        		// Confirm
        	}
        	
        }
        if(guibutton.id == 2)
        {
        	// Deny
        }
    }

    @Override
	public void drawScreen(int i, int j, float f)
    {
        playerList.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, title, width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }

    
    @Override
    public void updateScreen() {
    	super.updateScreen();
    }
    
    protected GuiScreen parent;
    protected String title;
    private GuiSlotApprove playerList;
    
    
	public static boolean playerIsOnline(String playerName) {
		NetClientHandler netclienthandler = ((EntityClientPlayerMP)ModLoader.getMinecraftInstance().thePlayer).sendQueue;
        java.util.List<?> list = netclienthandler.playerInfoList;
        int sz = list.size();
        int i = 0;
        while(i < sz) {
        	if(((GuiPlayerInfo) list.get(i)).name.equalsIgnoreCase(playerName)) {
        		return true;
        	}
        	i++;
        }
        return false;
	}
}

class GuiSlotApprove extends GuiSlot
{

    final GuiApproveMember parent; /* synthetic field */
    static Minecraft mc = ModLoader.getMinecraftInstance();

    public GuiSlotApprove(GuiApproveMember parent)
    {
    	super(mc, parent.width, parent.height, 32, parent.height - 64, 10);
    	results = new ConcurrentHashMap<String, String>();
        this.parent = parent;
        func_27258_a(false);
        getApprove();
    }
    
    protected void getApprove()
    {
    	// Api calls
    	results.put("test", "In-game");
    	results.put("test2", "Not In-game");
    }

    @Override
	protected int getSize()
    {
        return results.size();
    }

    @Override
	protected void elementClicked(int i, boolean flag)
    {
    	parent.selected = i;
    }

    @Override
	protected boolean isSelected(int i)
    {
        return i == parent.selected;
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
    
    public ConcurrentHashMap<String, String> results;

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
