package org.frantictools.franticmod.gui;

import net.minecraft.src.GuiAchievements;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiButtonLanguage;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiStats;
import net.minecraft.src.MathHelper;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StatList;

public class GuiFModMenu extends GuiScreen
{
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        byte byte0 = -16;
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + byte0, "Back to game"));


        controlList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + byte0, "Lookup Player"));
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + byte0, "Settings"));
        controlList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + byte0, 98, 20, "Ban Guest"));
        controlList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + byte0, 98, 20, "Approve Member"));
        
        controlList.add(new GuiButtonLanguage(7, width / 2 - 124, height / 4 + 120 + byte0));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
            case 2:
            case 3:
            default:
                break;

            case 0:
                mc.displayGuiScreen(new GuiManuelPage(this, "test", new String[]
                {
                		"Lorem ipsum dolor sit amet",
                		"amet sit dolor ipsum Lorem",
                		"stuff stuff stuff",
                		" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~⌂ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»",
                		"\247l\247m\247n\247otest\247r"
                }));
                break;

            case 1:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;

            case 4:
                mc.displayGuiScreen(new GuiSearch(this));
                break;

            case 5:
                mc.displayGuiScreen(new GuiBanGuest(this));
                break;

            case 6:
                mc.displayGuiScreen(new GuiApproveMember(this));
                break;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();

        drawCenteredString(fontRenderer, "Frantic Mod", width / 2, 40, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }

}
