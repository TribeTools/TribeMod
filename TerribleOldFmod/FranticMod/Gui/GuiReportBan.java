// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.minecraft.FranticMod.Gui;

import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ServerNBTStorage;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.mod_FranticMod;

import org.lwjgl.input.Keyboard;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiTextField, StringTranslate, GuiButton, 
//            ServerNBTStorage

public class GuiReportBan extends GuiScreen
{

    private GuiScreen field_35362_a;
    private GuiTextField reason;
    private GuiTextField name;
    private GuiTextField duration;
//    private GuiTextField imageUrl;

    public GuiReportBan(GuiScreen guiscreen)
    {
        field_35362_a = guiscreen;
    }

    public void updateScreen()
    {
        name.updateCursorCounter();
        reason.updateCursorCounter();
        duration.updateCursorCounter();
//        imageUrl.updateCursorCounter();
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 126 + 12, 98, 20, "Post"));
        controlList.add(new GuiButton(1, width / 2 + 2, height / 4 + 126 + 12, 98, 20, "Cancel"));
        
        name = new GuiTextField(this, fontRenderer, width / 2 - 100, 46, 200, 20, "");
        name.setMaxStringLength(32);
        
        reason = new GuiTextField(this, fontRenderer, width / 2 - 100, 86, 200, 20, "");
        reason.setMaxStringLength(128);
        
        duration = new GuiTextField(this, fontRenderer, width / 2 - 100, 126, 200, 20, "");
        duration.setMaxStringLength(128);
        
//        imageUrl = new GuiTextField(this, fontRenderer, width / 2 - 100, 166, 200, 20, "");
//        imageUrl.setMaxStringLength(128);
        
        name.isFocused = true;
        ((GuiButton)controlList.get(0)).enabled = reason.getText().length() > 0 && name.getText().length() > 0 && duration.getText().length() > 0 && (mod_FranticMod.imageUrl != null);
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
            mc.displayGuiScreen(field_35362_a);
        }
        if(guibutton.id == 0)
        {
        	UrlLib.PostBan(name.getText().trim(),reason.getText(),duration.getText());
        	mc.displayGuiScreen(field_35362_a);
        }
    }

    protected void keyTyped(char c, int i)
    {
        name.textboxKeyTyped(c, i);
        reason.textboxKeyTyped(c, i);
        if(c == '\t')
        {
            if(name.isFocused)
            {
                name.isFocused = false;
                reason.isFocused = true;
                duration.isEnabled = false;
            }
            if (reason.isFocused)
            {
                name.isFocused = false;
                reason.isFocused = false;
                duration.isFocused = true;
            }
            if (duration.isFocused)
            {
            	name.isFocused = true;
            	reason.isFocused = false;
            	duration.isFocused = false;
            }
        }
        if(c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
        }
        ((GuiButton)controlList.get(0)).enabled = reason.getText().length() > 0 && name.getText().length() > 0 && duration.getText().length() > 0 && (mod_FranticMod.imageUrl != null);
        if(((GuiButton)controlList.get(0)).enabled)
        {
            String s = reason.getText().trim();
            String as[] = s.split(":");
            if(as.length > 2)
            {
                ((GuiButton)controlList.get(0)).enabled = false;
            }
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        
        reason.mouseClicked(i, j, k);
        name.mouseClicked(i, j, k);
        duration.mouseClicked(i, j, k);
//        imageUrl.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Report a ban", width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, "Name", width / 2 - 100, 33, 0xa0a0a0);
        drawString(fontRenderer, "Reason", width / 2 - 100, 74, 0xa0a0a0);
        drawString(fontRenderer, "Duration", width / 2 - 100, 115, 0xa0a0a0);
        
        if (mod_FranticMod.imageUrl != null)
        	drawString(fontRenderer, "You need to take a screenshot first", width / 2 - 100, 156, 0xff0000);
        name.drawTextBox();
        reason.drawTextBox();
        duration.drawTextBox();
//        imageUrl.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
