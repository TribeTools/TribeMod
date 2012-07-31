package org.frantictools.franticmod.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Achievement;
import net.minecraft.src.Gui;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderItem;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StatCollector;

public class GuiMessage extends Gui
{
    /** Holds the instance of the game (Minecraft) */
    private Minecraft mc;

    /** Holds the latest width scaled to fit the game window. */
    private int messageWindowWidth;

    /** Holds the latest height scaled to fit the game window. */
    private int messageWindowHeight;
    private String messageTitle;
    private String messageLine;

    /** Holds the achievement that will be displayed on the GUI. */
 //   private Achievement theAchievement;
    private long messageTime;

    private ItemStack item;
    /**
     * Holds a instance of RenderItem, used to draw the achievement icons on screen (is based on ItemStack)
     */
    private RenderItem itemRender;
    private boolean fullMessage;

    public GuiMessage()
    {
        mc = ModLoader.getMinecraftInstance();
        itemRender = new RenderItem();
        System.out.println("contructed");
    }

    /**
     * Queue a taken achievement to be displayed.
     */
    public void queueMessageWithTitle(String title, String message, ItemStack item)
    {
        messageTitle = title;
        messageLine = message;
        messageTime = System.currentTimeMillis();
        this.item = item;
        fullMessage = false;
        System.out.println("queued: " + messageTime + " " + messageTitle + " " + messageLine);
    }

    /**
     * Queue a information about a achievement to be displayed.
     */
    public void queueMessage(String message, ItemStack item)
    {
        messageTitle = "";
        messageLine = message;
        messageTime = System.currentTimeMillis();
        this.item = item;
        fullMessage = true;
    }

    /**
     * Update the display of the achievement window to match the game window.
     */
    private void updateMessageWindowScale()
    {
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        messageWindowWidth = mc.displayWidth;
        messageWindowHeight = mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        messageWindowWidth = scaledresolution.getScaledWidth();
        messageWindowHeight = scaledresolution.getScaledHeight();
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, messageWindowWidth, messageWindowHeight, 0.0D, 1000D, 3000D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000F);
    }

    /**
     * Updates the small achievement tooltip window, showing a queued achievement if is needed.
     */
    public void updateMessageWindow()
    {
        if (messageTime == 0L)
        {
            return;
        }

        double d = (double)(System.currentTimeMillis() - messageTime) / 3000D;

        if (!fullMessage && (d < 0.0D || d > 1.0D))
        {
            messageTime = 0L;
            return;
        }

        updateMessageWindowScale();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        double d1 = d * 2D;

        if (d1 > 1.0D)
        {
            d1 = 2D - d1;
        }

        d1 *= 4D;
        d1 = 1.0D - d1;

        if (d1 < 0.0D)
        {
            d1 = 0.0D;
        }

        d1 *= d1;
        d1 *= d1;
        int i = messageWindowWidth - 160;
        int j = 0 - (int)(d1 * 36D);
        int k = mc.renderEngine.getTexture("/achievement/bg.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, k);
        GL11.glDisable(GL11.GL_LIGHTING);
        drawTexturedModalRect(i, j, 96, 202, 160, 32);

        if (fullMessage)
        {
            mc.fontRenderer.drawSplitString(messageLine, i + 30, j + 7, 120, -1);
        }
        else
        {
            mc.fontRenderer.drawString(messageTitle, i + 30, j + 7, -256);
            mc.fontRenderer.drawString(messageLine, i + 30, j + 18, -1);
        }

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        itemRender.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, i + 8, j + 8);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
}
