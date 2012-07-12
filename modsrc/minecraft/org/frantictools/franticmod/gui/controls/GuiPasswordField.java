package org.frantictools.franticmod.gui.controls;

import org.frantictools.franticmod.util.StringUtils;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiTextField;

public class GuiPasswordField extends GuiTextField
{

	public GuiPasswordField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5)
	{
		super(par1FontRenderer, par2, par3, par4, par5);
	}

    /**
     * Draws the textbox
     */
    public void drawTextBox()
    {
    	String oldText = text;
    	text = StringUtils.repeat("*", text.length());
    	
        if (getEnableBackgroundDrawing())
        {
            drawRect(xPos - 1, yPos - 1, xPos + width + 1, yPos + height + 1, 0xffa0a0a0);
            drawRect(xPos, yPos, xPos + width, yPos + height, 0xff000000);
        }

        int i = isEnabled ? enabledColor : disabledColor;
        int j = field_50042_o - field_50041_n;
        int k = field_50048_p - field_50041_n;
        String s = fontRenderer.trimStringToWidth(text.substring(field_50041_n), func_50019_l());
        boolean flag = j >= 0 && j <= s.length();
        boolean flag1 = isFocused && (cursorCounter / 6) % 2 == 0 && flag;
        int l = enableBackgroundDrawing ? xPos + 4 : xPos;
        int i1 = enableBackgroundDrawing ? yPos + (height - 8) / 2 : yPos;
        int j1 = l;

        if (k > s.length())
        {
            k = s.length();
        }

        if (s.length() > 0)
        {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = fontRenderer.drawStringWithShadow(s1, j1, i1, i);
        }

        boolean flag2 = field_50042_o < text.length() || text.length() >= func_50040_g();
        int k1 = j1;

        if (!flag)
        {
            k1 = j <= 0 ? l : l + width;
        }
        else if (flag2)
        {
            k1--;
            j1--;
        }

        if (s.length() > 0 && flag && j < s.length())
        {
            j1 = fontRenderer.drawStringWithShadow(s.substring(j), j1, i1, i);
        }

        if (flag1)
        {
            if (flag2)
            {
                Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRenderer.FONT_HEIGHT, 0xffd0d0d0);
            }
            else
            {
                fontRenderer.drawStringWithShadow("_", k1, i1, i);
            }
        }

        if (k != j)
        {
            int l1 = l + fontRenderer.getStringWidth(s.substring(0, k));
            func_50029_c(k1, i1 - 1, l1 - 1, i1 + 1 + fontRenderer.FONT_HEIGHT);
        }
        
        text = oldText;
    }
}
