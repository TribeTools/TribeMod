package org.frantictools.franticmod.gui;

import org.frantictools.franticmod.gui.controls.GuiToggleButton;

import net.minecraft.src.GuiAchievements;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiStats;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatList;

public class GuiTest extends GuiScreen {
    /** Also counts the number of updates, not certain as to why yet. */
    private int updateCounter2;

    /** Counts the number of screen updates. */
    private int updateCounter;

    public GuiTest()
    {
        updateCounter2 = 0;
        updateCounter = 0;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        updateCounter2 = 0;
        controlList.clear();
        byte byte0 = -16;
        controlList.add(new GuiToggleButton(1, width / 2 - 100, height / 4 + 120 + byte0, "tets"));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	if (par1GuiButton instanceof GuiToggleButton)
    	{
    		((GuiToggleButton)par1GuiButton).toggleState = !((GuiToggleButton)par1GuiButton).toggleState;
    	}
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        updateCounter++;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();


        drawCenteredString(fontRenderer, ((Boolean)((GuiToggleButton) controlList.get(0)).toggleState).toString(), width / 2, 40, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
