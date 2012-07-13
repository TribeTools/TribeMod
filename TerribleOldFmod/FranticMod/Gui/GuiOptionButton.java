package net.minecraft.FranticMod.Gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.FranticMod.Util.ChatColors;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class GuiOptionButton extends GuiButton {
	
	public GuiOptionButton(int i, int j, int k, String s)
    {
        super(i, j, k, 200, 20, s);
    }
	public GuiOptionButton(int i, int j, int k, int l, int i1, String s)
    {
        super(i, j, k, l, i1, s);
    }
	
	public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (!drawButton)
        {
            return;
        }
        FontRenderer fontrenderer = minecraft.fontRenderer;
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, minecraft.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
        int k = getHoverState(flag);
        drawTexturedModalRect(xPosition, yPosition, 0, 46 + k * 20, width / 2, height);
        drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + k * 20, width / 2, height);
        mouseDragged(minecraft, i, j);
        String s = (ticked ? ChatColors.BrightGreen : ChatColors.Red) + displayString;
        if (!enabled)
        {
            drawCenteredString(fontrenderer, s, xPosition + width / 2, yPosition + (height - 8) / 2, 0xffa0a0a0);
        }
        else if (flag)
        {
            drawCenteredString(fontrenderer, s, xPosition + width / 2, yPosition + (height - 8) / 2, 0xffffa0);
        }
        else
        {
            drawCenteredString(fontrenderer, s, xPosition + width / 2, yPosition + (height - 8) / 2, 0xe0e0e0);
        }
    }
	
	public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
		ticked = !ticked;
        return super.mousePressed(minecraft, i, j);
    }
	
	public boolean ticked;
}
