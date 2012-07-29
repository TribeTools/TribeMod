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

import org.frantictools.franticapi.FranticMeAPI;
import org.frantictools.franticapi.ICallback;
import org.frantictools.franticmod.FranticMod;
import org.frantictools.franticmod.gui.controls.GuiPasswordField;
import org.frantictools.franticmod.gui.controls.GuiToggleButton;
import org.frantictools.franticmod.util.ApiLib;

import org.lwjgl.input.Keyboard;

public class GuiLogin extends GuiScreen {
    private GuiTextField passwordField;
    private GuiTextField userField;

    String title = "Login";
	String flash = "";
	int flashColor = 0xFFFFFF;
	
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
        controlList.add(new GuiButton(1, width / 2 - 100, (height / 4 + 48) + 72 + 12, 98, 20, "Cancel"));
        controlList.add(new GuiToggleButton(2, width / 2 + 2, (height / 4 + 48) + 72 + 12, 98, 20, "Rember", false));
        
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
        
        if (par1GuiButton.id == 0)
        {
        	flash = "Logging in, please wait.";
        	flashColor = 0xF0F050;
        	((GuiButton)controlList.get(0)).enabled = false;
        	((GuiButton)controlList.get(1)).enabled = false;
        	((GuiButton)controlList.get(2)).enabled = false;
        	
        	FranticMod.fm = new FranticMeAPI(userField.getText(), passwordField.getText());
        	FranticMod.fm.login(new ICallback<Boolean>() { public void onFinish(Boolean result)
			{
				if (result)
				{
					mc.displayGuiScreen(null);
					FranticMod.loggedIn = true;
				} else
				{
					flash = "Couldn't login. Is your information correct?";
					flashColor = 0xF05050;
					FranticMod.fm = null;
					((GuiButton)controlList.get(0)).enabled = passwordField.getText().length() > 0 && userField.getText().length() > 0;
					((GuiButton)controlList.get(1)).enabled = true;
					((GuiButton)controlList.get(2)).enabled = true;
				}
			}});
        }
        
        if (par1GuiButton.id == 2)
        	((GuiToggleButton) par1GuiButton).toggleState = !((GuiToggleButton) par1GuiButton).toggleState;
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
        drawCenteredString(fontRenderer, flash, width / 2, (height / 4 - 60) + 50, flashColor);
        
        drawString(fontRenderer, "Username:", width / 2 - 100, 63, 0xa0a0a0);
        drawString(fontRenderer, "Password:", width / 2 - 100, 104, 0xa0a0a0);
        userField.drawTextBox();
        passwordField.drawTextBox();
        
        super.drawScreen(par1, par2, par3);
    }
}
