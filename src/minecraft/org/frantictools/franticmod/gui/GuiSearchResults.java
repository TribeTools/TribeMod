package org.frantictools.franticmod.gui;

import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Tessellator;


public class GuiSearchResults extends GuiScreen
{
	
	public static int selected = 0;
	private String searchQuery;

    public GuiSearchResults(GuiScreen guiscreen, String query)
    {
        title = "";
        playerList = null;
        parentGui = guiscreen;
        searchQuery = query;
    }

    public void updateScreen() {
    	((GuiButton)controlList.get(1)).enabled = playerList.getSize() > 0;
    }
    
    @Override
	public void initGui()
    {
    	title = "Search Results";
        playerList = new GuiSlotPlayer(this, searchQuery);
        playerList.registerScrollButtons(controlList, 1, 1);

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
//        	if(selectedSlot.results.keySet().toArray(new String[] {})[selected] != null)
//        		mc.displayGuiScreen(new GuiPlayerInfo(selctedSlot.results.keySet().toArray(new String[] {})[selected]));
//        	else
//        		mod_FranticMod.showModMessage("FMod - Error", "You must select a name", Block.tnt.blockID);
        	
        }
    }

    @Override
	public void drawScreen(int i, int j, float f)
    {
        playerList.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, "Search Results (" + playerList.getSize() + ")", width / 2, 20, 0xffffff);
        super.drawScreen(i, j, f);
    }


    protected GuiScreen parentGui;
    protected String title;
    private GuiSlotPlayer playerList;

}


class GuiSlotPlayer extends GuiSlot
{

    final GuiScreen parent; /* synthetic field */
    static Minecraft mc = ModLoader.getMinecraftInstance();

    public GuiSlotPlayer(GuiSearchResults guiplayerlist, String searchQuery)
    {
    	super(mc, 40, guiplayerlist.height, 32, guiplayerlist.height - 64, 10);
    	results = new ConcurrentHashMap<String, String>();
        parent = guiplayerlist;
        func_27258_a(false);

        	showResults(searchQuery);
    }
    
    protected void showResults(String AQuery)
    {
    	results.put("improv32", "Moderator");
    	results.put("imprvot22", "Regular");
    	results.put("impwfwrov32", "Moderfweator");
    	results.put("impfewfrvot22", "Regfewfular");

    }

    @Override
	public int getSize()
    {
        return results.size();
    }

    @Override
	protected void elementClicked(int i, boolean flag)
    {
    	GuiSearchResults.selected = i;
    }

    @Override
	protected boolean isSelected(int i)
    {
        return i == GuiSearchResults.selected;
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
        mc.fontRenderer.drawString(name, j + 2, k + 1, isSelected(i) ? 0x00ff00 : 0xffffff);
    	
        String rank = localResults.get(localResults.keySet().toArray(new String[] {})[i]);
        rank = isSelected(i) ? rank + " <" : rank;
        mc.fontRenderer.drawString(rank, (j + 2 + 213) - mc.fontRenderer.getStringWidth(rank), k + 1, isSelected(i) ? 0x00ff00 : 0xffffff);
    }
}
