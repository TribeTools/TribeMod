// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.FranticMod.Gui;

import net.minecraft.FranticMod.Util.ChatColors;
import net.minecraft.FranticMod.Util.Config;
import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ServerNBTStorage;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.mod_FranticMod;

import org.lwjgl.input.Keyboard;
import java.util.HashMap;
// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiTextField, StringTranslate, GuiButton, 
//            ServerNBTStorage

public class GuiLogin extends GuiScreen {

	private GuiScreen field_35362_a;
	private GuiTextField password;
	private GuiTextField userName;

	private String pass = "";
	private String title = "Login";
	
	public boolean willRemember = Boolean.parseBoolean(Config.props.getProperty("autologin"));

	public GuiLogin(GuiScreen guiscreen) {
		field_35362_a = guiscreen;
	}

	public void updateScreen() {
		userName.updateCursorCounter();
		password.updateCursorCounter();
		if (mod_FranticMod.loggedIn)
			mc.displayGuiScreen(field_35362_a);
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Login"));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, 98, 20, "Cancel"));
		controlList.add(new GuiButton(2, width / 2 + 2, height / 4 + 120 + 12, 98, 20, ((willRemember) ? ChatColors.BrightGreen : ChatColors.Red) + "Remember"));
		
		userName = new GuiTextField(this, fontRenderer, width / 2 - 100, 76, 200, 20, "");

		userName.isFocused = true;
		userName.setMaxStringLength(32);

		password = new GuiTextField(this, fontRenderer, width / 2 - 100, 116, 200, 20, "");
		password.setMaxStringLength(128);
		((GuiButton) controlList.get(0)).enabled = password.getText().length() > 0 && userName.getText().length() > 0;
		
		if(Config.props.getProperty("username") != "" && Config.props.getProperty("password") != "" && Boolean.parseBoolean(Config.props.getProperty("autologin"))) {
			userName.setText(Config.props.getProperty("username"));
			pass = Config.props.getProperty("password");
			String mask = "";
			for (int p = 0; p < password.getText().length(); p++) {
				mask += "#";
			}
			password.setText(mask);
        	((GuiButton) controlList.get(2)).displayString = ((Boolean.parseBoolean(Config.props.getProperty("autologin"))) ? ChatColors.BrightGreen : ChatColors.Red) + "Remember";
			try {
				userName.isEnabled = false;
				password.isEnabled = false;
				((GuiButton) controlList.get(0)).enabled = false;
				UrlLib.PerformLogin(userName.getText(), pass, true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onGuiClosed() {
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
            mc.displayGuiScreen(field_35362_a);
        } else
        if(guibutton.id == 0)
        {

           try {
        	   UrlLib.PerformLogin(userName.getText(), pass, willRemember);
        	   mc.displayGuiScreen(field_35362_a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        if (guibutton.id == 2)
        {
        	willRemember = !willRemember;
        	Config.props.setProperty("autologin", String.valueOf(willRemember));
        	((GuiButton) controlList.get(2)).displayString = ((willRemember) ? ChatColors.BrightGreen : ChatColors.Red) + "Remember";
        }
			
        
    }

	protected void keyTyped(char c, int i) {
		userName.textboxKeyTyped(c, i);
		password.textboxKeyTyped(c, i);

		if (c == '\b' && password.isFocused) {
			if(pass.length() > 0)
				pass = pass.substring(0, pass.length() - 1);
		} else if (password.isFocused && (c != '\r')) {
			pass += c;
		}

		String mask = "";

		for (int p = 0; p < password.getText().length(); p++) {
			mask += "#";
		}
		password.setText(mask);

		if (c == '\t') {
			if (userName.isFocused) {
				userName.isFocused = false;
				password.isFocused = true;
			} else {
				userName.isFocused = true;
				password.isFocused = false;
			}
		}
		((GuiButton) controlList.get(0)).enabled = password.getText().length() > 0
				&& userName.getText().length() > 0;
		if (c == '\r') {
			userName.isEnabled = false;
			password.isEnabled = false;
			((GuiButton) controlList.get(0)).enabled = false;
			try {
	        	   UrlLib.PerformLogin(userName.getText(), pass, willRemember);
	        	   mc.displayGuiScreen(field_35362_a);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	protected void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		password.mouseClicked(i, j, k);
		userName.mouseClicked(i, j, k);
	}

	public void drawScreen(int i, int j, float f) {
		StringTranslate stringtranslate = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, title, width / 2,
				(height / 4 - 60) + 20, 0xffffff);
		drawString(fontRenderer, "Username", width / 2 - 100, 63, 0xa0a0a0);
		drawString(fontRenderer, "Password", width / 2 - 100, 104, 0xa0a0a0);
		userName.drawTextBox();
		password.drawTextBox();
		super.drawScreen(i, j, f);
	}
}
