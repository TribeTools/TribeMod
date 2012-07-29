package org.frantictools.franticmod.gui.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;

import org.lwjgl.opengl.GL11;

/** Basically a checkbox, due to notch being retarded the caller must toggle the state manually using actionPerformed. */
public class GuiToggleButton extends GuiButton
{
    
    /** Whether the button is checked or not. */
    public boolean toggleState;

    public GuiToggleButton(int id, int xPos, int yPos, String text)
    {
        this(id, xPos, yPos, 200, 20, text, false);
    }

    public GuiToggleButton(int id, int xPos, int yPos, int width, int height, String text, boolean state)
    {
    	super(id, xPos, yPos, width, height, text);
        this.toggleState = state;
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (!drawButton)
        {
            return;
        }

        FontRenderer fontrenderer = par1Minecraft.fontRenderer;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, par1Minecraft.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int i = getHoverState(flag);
        drawTexturedModalRect(xPosition, yPosition, 0, 46 + i * 20, width / 2, height);
        drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + i * 20, width / 2, height);
        mouseDragged(par1Minecraft, par2, par3);
        int j = 0xe0e0e0;

        if (!enabled)
        {
            j = 0xffa0a0a0;
        }
        else if (flag)
        {
            j = 0xffffa0;
        }
        
        j = toggleState? 0x50F050 : 0xF05050;
        
        drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, j);
    }
    
}
