package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiTextField extends Gui
{
    /**
     * Have the font renderer from GuiScreen to render the textbox text into the screen.
     */
    protected final FontRenderer fontRenderer;
    protected final int xPos;
    protected final int yPos;

    /** The width of this text field. */
    protected final int width;
    protected final int height;

    /** Have the current text beign edited on the textbox. */
    protected String text;
    protected int maxStringLength;
    protected int cursorCounter;
    protected boolean enableBackgroundDrawing;

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    protected boolean canLoseFocus;

    /**
     * If this value is true along isEnabled, keyTyped will process the keys.
     */
    protected boolean isFocused;

    /**
     * If this value is true along isFocused, keyTyped will process the keys.
     */
    protected boolean isEnabled;
    protected int field_50041_n;
    protected int field_50042_o;
    protected int field_50048_p;
    protected int enabledColor;
    protected int disabledColor;

    public GuiTextField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5)
    {
        text = "";
        maxStringLength = 32;
        enableBackgroundDrawing = true;
        canLoseFocus = true;
        isFocused = false;
        isEnabled = true;
        field_50041_n = 0;
        field_50042_o = 0;
        field_50048_p = 0;
        enabledColor = 0xe0e0e0;
        disabledColor = 0x707070;
        fontRenderer = par1FontRenderer;
        xPos = par2;
        yPos = par3;
        width = par4;
        height = par5;
    }

    /**
     * Increments the cursor counter
     */
    public void updateCursorCounter()
    {
        cursorCounter++;
    }

    /**
     * Sets the text of the textbox.
     */
    public void setText(String par1Str)
    {
        if (par1Str.length() > maxStringLength)
        {
            text = par1Str.substring(0, maxStringLength);
        }
        else
        {
            text = par1Str;
        }

        func_50038_e();
    }

    /**
     * Returns the text beign edited on the textbox.
     */
    public String getText()
    {
        return text;
    }

    public String func_50039_c()
    {
        int i = field_50042_o >= field_50048_p ? field_50048_p : field_50042_o;
        int j = field_50042_o >= field_50048_p ? field_50042_o : field_50048_p;
        return text.substring(i, j);
    }

    public void func_50031_b(String par1Str)
    {
        String s = "";
        String s1 = ChatAllowedCharacters.filerAllowedCharacters(par1Str);
        int i = field_50042_o >= field_50048_p ? field_50048_p : field_50042_o;
        int j = field_50042_o >= field_50048_p ? field_50042_o : field_50048_p;
        int k = maxStringLength - text.length() - (i - field_50048_p);
        int l = 0;

        if (text.length() > 0)
        {
            s = (new StringBuilder()).append(s).append(text.substring(0, i)).toString();
        }

        if (k < s1.length())
        {
            s = (new StringBuilder()).append(s).append(s1.substring(0, k)).toString();
            l = k;
        }
        else
        {
            s = (new StringBuilder()).append(s).append(s1).toString();
            l = s1.length();
        }

        if (text.length() > 0 && j < text.length())
        {
            s = (new StringBuilder()).append(s).append(text.substring(j)).toString();
        }

        text = s;
        func_50023_d((i - field_50048_p) + l);
    }

    public void func_50021_a(int par1)
    {
        if (text.length() == 0)
        {
            return;
        }

        if (field_50048_p != field_50042_o)
        {
            func_50031_b("");
            return;
        }
        else
        {
            func_50020_b(func_50028_c(par1) - field_50042_o);
            return;
        }
    }

    public void func_50020_b(int par1)
    {
        if (text.length() == 0)
        {
            return;
        }

        if (field_50048_p != field_50042_o)
        {
            func_50031_b("");
            return;
        }

        boolean flag = par1 < 0;
        int i = flag ? field_50042_o + par1 : field_50042_o;
        int j = flag ? field_50042_o : field_50042_o + par1;
        String s = "";

        if (i >= 0)
        {
            s = text.substring(0, i);
        }

        if (j < text.length())
        {
            s = (new StringBuilder()).append(s).append(text.substring(j)).toString();
        }

        text = s;

        if (flag)
        {
            func_50023_d(par1);
        }
    }

    public int func_50028_c(int par1)
    {
        return func_50024_a(par1, func_50035_h());
    }

    public int func_50024_a(int par1, int par2)
    {
        int i = par2;
        boolean flag = par1 < 0;
        int j = Math.abs(par1);

        for (int k = 0; k < j; k++)
        {
            if (flag)
            {
                for (; i > 0 && text.charAt(i - 1) == ' '; i--) { }

                for (; i > 0 && text.charAt(i - 1) != ' '; i--) { }

                continue;
            }

            int l = text.length();
            i = text.indexOf(' ', i);

            if (i == -1)
            {
                i = l;
                continue;
            }

            for (; i < l && text.charAt(i) == ' '; i++) { }
        }

        return i;
    }

    public void func_50023_d(int par1)
    {
        func_50030_e(field_50048_p + par1);
    }

    public void func_50030_e(int par1)
    {
        field_50042_o = par1;
        int i = text.length();

        if (field_50042_o < 0)
        {
            field_50042_o = 0;
        }

        if (field_50042_o > i)
        {
            field_50042_o = i;
        }

        func_50032_g(field_50042_o);
    }

    public void func_50034_d()
    {
        func_50030_e(0);
    }

    public void func_50038_e()
    {
        func_50030_e(text.length());
    }

    /**
     * Call this method from you GuiScreen to process the keys into textbox.
     */
    public boolean textboxKeyTyped(char par1, int par2)
    {
        if (!isEnabled || !isFocused)
        {
            return false;
        }

        switch (par1)
        {
            case 1:
                func_50038_e();
                func_50032_g(0);
                return true;

            case 3:
                GuiScreen.setClipboardString(func_50039_c());
                return true;

            case 22:
                func_50031_b(GuiScreen.getClipboardString());
                return true;

            case 24:
                GuiScreen.setClipboardString(func_50039_c());
                func_50031_b("");
                return true;
        }

        switch (par2)
        {
            case 203:
                if (GuiScreen.isShiftKeyDown())
                {
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        func_50032_g(func_50024_a(-1, func_50036_k()));
                    }
                    else
                    {
                        func_50032_g(func_50036_k() - 1);
                    }
                }
                else if (GuiScreen.isCtrlKeyDown())
                {
                    func_50030_e(func_50028_c(-1));
                }
                else
                {
                    func_50023_d(-1);
                }

                return true;

            case 205:
                if (GuiScreen.isShiftKeyDown())
                {
                    if (GuiScreen.isCtrlKeyDown())
                    {
                        func_50032_g(func_50024_a(1, func_50036_k()));
                    }
                    else
                    {
                        func_50032_g(func_50036_k() + 1);
                    }
                }
                else if (GuiScreen.isCtrlKeyDown())
                {
                    func_50030_e(func_50028_c(1));
                }
                else
                {
                    func_50023_d(1);
                }

                return true;

            case 14:
                if (GuiScreen.isCtrlKeyDown())
                {
                    func_50021_a(-1);
                }
                else
                {
                    func_50020_b(-1);
                }

                return true;

            case 211:
                if (GuiScreen.isCtrlKeyDown())
                {
                    func_50021_a(1);
                }
                else
                {
                    func_50020_b(1);
                }

                return true;

            case 199:
                if (GuiScreen.isShiftKeyDown())
                {
                    func_50032_g(0);
                }
                else
                {
                    func_50034_d();
                }

                return true;

            case 207:
                if (GuiScreen.isShiftKeyDown())
                {
                    func_50032_g(text.length());
                }
                else
                {
                    func_50038_e();
                }

                return true;
        }

        if (ChatAllowedCharacters.isAllowedCharacter(par1))
        {
            func_50031_b(Character.toString(par1));
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int par1, int par2, int par3)
    {
        boolean flag = par1 >= xPos && par1 < xPos + width && par2 >= yPos && par2 < yPos + height;

        if (canLoseFocus)
        {
            setFocused(isEnabled && flag);
        }

        if (isFocused && par3 == 0)
        {
            int i = par1 - xPos;

            if (enableBackgroundDrawing)
            {
                i -= 4;
            }

            String s = fontRenderer.trimStringToWidth(text.substring(field_50041_n), func_50019_l());
            func_50030_e(fontRenderer.trimStringToWidth(s, i).length() + field_50041_n);
        }
    }

    /**
     * Draws the textbox
     */
    public void drawTextBox()
    {
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
    }

    protected void func_50029_c(int par1, int par2, int par3, int par4)
    {
        if (par1 < par3)
        {
            int i = par1;
            par1 = par3;
            par3 = i;
        }

        if (par2 < par4)
        {
            int j = par2;
            par2 = par4;
            par4 = j;
        }

        Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(0.0F, 0.0F, 255F, 255F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glLogicOp(GL11.GL_OR_REVERSE);
        tessellator.startDrawingQuads();
        tessellator.addVertex(par1, par4, 0.0D);
        tessellator.addVertex(par3, par4, 0.0D);
        tessellator.addVertex(par3, par2, 0.0D);
        tessellator.addVertex(par1, par2, 0.0D);
        tessellator.draw();
        GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void setMaxStringLength(int par1)
    {
        maxStringLength = par1;

        if (text.length() > par1)
        {
            text = text.substring(0, par1);
        }
    }

    public int func_50040_g()
    {
        return maxStringLength;
    }

    public int func_50035_h()
    {
        return field_50042_o;
    }

    /**
     * get enable drawing background and outline
     */
    public boolean getEnableBackgroundDrawing()
    {
        return enableBackgroundDrawing;
    }

    /**
     * enable drawing background and outline
     */
    public void setEnableBackgroundDrawing(boolean par1)
    {
        enableBackgroundDrawing = par1;
    }

    /**
     * setter for the focused field
     */
    public void setFocused(boolean par1)
    {
        if (par1 && !isFocused)
        {
            cursorCounter = 0;
        }

        isFocused = par1;
    }

    /**
     * getter for the focused field
     */
    public boolean getIsFocused()
    {
        return isFocused;
    }

    public int func_50036_k()
    {
        return field_50048_p;
    }

    public int func_50019_l()
    {
        return getEnableBackgroundDrawing() ? width - 8 : width;
    }

    public void func_50032_g(int par1)
    {
        int i = text.length();

        if (par1 > i)
        {
            par1 = i;
        }

        if (par1 < 0)
        {
            par1 = 0;
        }

        field_50048_p = par1;

        if (fontRenderer != null)
        {
            if (field_50041_n > i)
            {
                field_50041_n = i;
            }

            int j = func_50019_l();
            String s = fontRenderer.trimStringToWidth(text.substring(field_50041_n), j);
            int k = s.length() + field_50041_n;

            if (par1 == field_50041_n)
            {
                field_50041_n -= fontRenderer.trimStringToWidth(text, j, true).length();
            }

            if (par1 > k)
            {
                field_50041_n += par1 - k;
            }
            else if (par1 <= field_50041_n)
            {
                field_50041_n -= field_50041_n - par1;
            }

            if (field_50041_n < 0)
            {
                field_50041_n = 0;
            }

            if (field_50041_n > i)
            {
                field_50041_n = i;
            }
        }
    }

    /**
     * if true the textbox can lose focus by clicking elsewhere on the screen
     */
    public void setCanLoseFocus(boolean par1)
    {
        canLoseFocus = par1;
    }
}
