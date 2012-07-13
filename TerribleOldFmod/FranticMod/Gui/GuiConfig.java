package net.minecraft.FranticMod.Gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.mod_FranticMod;

public class GuiConfig extends GuiScreen {
	private GuiScreen parent;
	public GuiConfig(GuiScreen parent) {
		this.parent = parent;
	}
	
	public void initGui()
    {
        controlList.clear();
        byte byte0 = -16;
        
        controlList.add(new GuiOptionButton(4, width / 2 - 100, height / 4 + 24 + byte0, "Lookup"));
        controlList.add(new GuiOptionButton(5, width / 2 - 100, height / 4 + 48 + byte0, 98, 20, "Approve"));
        
        controlList.add(new GuiOptionButton(6, width / 2 + 2, height / 4 + 48 + byte0, 98, 20, "Report Ban"));
        
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + byte0, "done"));
        
    }

    protected void actionPerformed(GuiButton guibutton)
    {
    	if(guibutton.id == 0)
    	{
    		mc.displayGuiScreen(parent);
    	}
    	
    	//TODO Check toggles and update setting accordingly
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        
        drawCenteredString(fontRenderer, "FMod Config", width / 2, 40, 0xffffff);
        super.drawScreen(i, j, f);
    }
}
