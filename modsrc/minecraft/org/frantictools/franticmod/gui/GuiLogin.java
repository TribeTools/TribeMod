package org.frantictools.franticmod.gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ModLoader;

import org.frantictools.franticmod.FranticMod;
import org.frantictools.franticmod.gui.controls.GuiPasswordField;
import org.frantictools.franticmod.util.ApiLib;

import org.lwjgl.input.Keyboard;

public class GuiLogin extends GuiScreen {
    private GuiTextField passwordField;
    private GuiTextField userField;

    String title = "Login";
    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        userField.updateCursorCounter();
        passwordField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Login"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Cancel"));
        
        userField = new GuiTextField(fontRenderer, width / 2 - 100, 76, 200, 20);
        userField.setFocused(true);
        userField.setText("");
        
        passwordField = new GuiPasswordField(fontRenderer, width / 2 - 100, 116, 200, 20);
        passwordField.setMaxStringLength(128);
        passwordField.setText("");
        
        ((GuiButton)controlList.get(0)).enabled = passwordField.getText().length() > 0 && userField.getText().length() > 0;
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
        	mc.displayGuiScreen(null);
        }
        else if (par1GuiButton.id == 0)
        {
             if (ApiLib.login(userField.getText(), passwordField.getText())) {
            	 FranticMod.loggedIn = true;
            	 mc.displayGuiScreen(null);
             } else {
            	 title = "Bad Username/Password";
             }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        userField.textboxKeyTyped(par1, par2);
        passwordField.textboxKeyTyped(par1, par2);

        if (par1 == '\t')
        {
            if (userField.getIsFocused())
            {
                userField.setFocused(false);
                passwordField.setFocused(true);
            }
            else
            {
                userField.setFocused(true);
                passwordField.setFocused(false);
            }
        }

        if (par1 == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }

        ((GuiButton)controlList.get(0)).enabled = passwordField.getText().length() > 0 && userField.getText().length() > 0;

        if (((GuiButton)controlList.get(0)).enabled)
        {
            String s = passwordField.getText().trim();
            String as[] = s.split(":");

            if (as.length > 2)
            {
                ((GuiButton)controlList.get(0)).enabled = false;
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        passwordField.mouseClicked(par1, par2, par3);
        userField.mouseClicked(par1, par2, par3);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, title, width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Username:", width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, "Password:", width / 2 - 100, 104, 0xa0a0a0);
        userField.drawTextBox();
        passwordField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
