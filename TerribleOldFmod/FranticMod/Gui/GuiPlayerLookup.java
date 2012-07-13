package net.minecraft.FranticMod.Gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ModLoader;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.WorldInfo;

public class GuiPlayerLookup extends GuiScreen {
	private GuiScreen parentGuiScreen;
    private GuiTextField textField;

    public GuiPlayerLookup(GuiScreen guiscreen)
    {
        parentGuiScreen = guiscreen;
    }

    public void updateScreen()
    {
        textField.updateCursorCounter();
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Lookup"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));

        textField = new GuiTextField(this, fontRenderer, width / 2 - 100, 60, 200, 20, "");
        textField.isFocused = true;
        textField.setMaxStringLength(32);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(parentGuiScreen);
        } else
        if(guibutton.id == 0)
        {
        	//ModLoader.OpenGUI(mc.thePlayer, new GuiPlayerInfo(textField.getText().trim()));
        	ModLoader.OpenGUI(mc.thePlayer, new GuiPlayerList(this, textField.getText().trim()));
        }
    }

    protected void keyTyped(char c, int i)
    {
        textField.textboxKeyTyped(c, i);
        ((GuiButton)controlList.get(0)).enabled = textField.getText().trim().length() > 0;
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        textField.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Lookup Player", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Enter the name of the player to lookup.", width / 2 - 100, 47, 0xa0a0a0);
        textField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
