package org.frantictools.franticmod.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.WorldInfo;

public class GuiSearch extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    private GuiTextField searchField;

    public GuiSearch(GuiScreen par1GuiScreen)
    {
        parentGuiScreen = par1GuiScreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        searchField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Search"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));

        searchField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        searchField.setFocused(true);
        searchField.setText("");
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (!par1GuiButton.enabled)
        {
            return;
        }

        if (par1GuiButton.id == 1)
        {
            mc.displayGuiScreen(parentGuiScreen);
        }
        else if (par1GuiButton.id == 0)
        {
        	mc.displayGuiScreen(new GuiSearchResults(this, searchField.getText()));
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        searchField.textboxKeyTyped(par1, par2);
        ((GuiButton)controlList.get(0)).enabled = searchField.getText().trim().length() > 0;

        if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        searchField.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Lookup Player", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Enter Name", width / 2 - 100, 47, 0xa0a0a0);
        searchField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}

