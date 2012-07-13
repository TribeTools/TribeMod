package net.minecraft.FranticMod.Gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.FranticMod.FranticMod;
import net.minecraft.FranticMod.Util.UrlLib;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.mod_FranticMod;

import java.awt.Color;
import java.util.HashMap;

public class GuiBabyInfo extends GuiScreen{
	private GuiScreen parentGuiScreen;
    
    private float xSize_lo;
    private float ySize_lo;

    private String player;
    private String sponsor;
    private String upvotes;
    private String downvotes;
    private String reason;
    
    @SuppressWarnings("unchecked")
	public GuiBabyInfo(String name)
    {
    	//String[] lTempArray = {lTempSponsor,lTempSponsoree,lTempUpVotes,lTempDownVotes,lTempReason};
    	player 		= 	name;
    	sponsor 		= 	((String[]) ((HashMap) FranticMod.tempData.get("babyData")).get(name))[0];
    	upvotes 	= 	((String[]) ((HashMap) FranticMod.tempData.get("babyData")).get(name))[2];
    	downvotes 	= 	((String[]) ((HashMap) FranticMod.tempData.get("babyData")).get(name))[3];
    	reason 	= 	((String[]) ((HashMap) FranticMod.tempData.get("babyData")).get(name))[4];
    	
    }
    
    @Override
    public void updateScreen()
    {

    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 85, height / 4 + 124, 80, 20, "UpVote"));
        controlList.add(new GuiButton(1, width / 2 + 4, height / 4 + 124, 80, 20, "DownVote"));
        controlList.add(new GuiButton(2, width / 2 + 65, height / 2 - 82, 20, 20, "X"));

    }
    
    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        
        if(guibutton.id == 0)
        {
        	UrlLib.castBabyVote(player,"up");
        	mc.displayGuiScreen(parentGuiScreen);
        }
        
        if(guibutton.id == 1)
        {
        	UrlLib.castBabyVote(player,"down");
        	mc.displayGuiScreen(parentGuiScreen);
        }
        
        if(guibutton.id == 2)
        {
        	mc.displayGuiScreen(parentGuiScreen);
        }
    }
    
    @Override
    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawBackgroundContainer(f, i, j);
        
        
        fontRenderer.drawStringWithShadow("Player Information", width / 2 - 84, height / 4 - 20, 0x00FFFF);
        
        fontRenderer.drawString("Username:", width / 2 - 84, height / 4, 0x404040);
        fontRenderer.drawString("Sponsor:", width / 2 - 84, height / 4 + 10, 0x404040);
        fontRenderer.drawString("Upvotes:", width / 2 - 84, height / 4 + 20, 0x404040);
        fontRenderer.drawString("Downvotes:", width / 2 - 84, height / 4 + 30, 0x404040);
        fontRenderer.drawString("Reason:", width / 2 - 84, height / 4 + 40, 0x404040);
        
        if(mod_FranticMod.playerIsOnline(player))
        	fontRenderer.drawString(player, width / 2 -30, height / 4, 0x3ADF00);
        else
        	fontRenderer.drawString(player, width / 2 -30, height / 4, 0x404040);
        
        fontRenderer.drawString(sponsor, width / 2 -30, height / 4 + 10, 0x404040);
        fontRenderer.drawString(upvotes, width / 2 -30, height / 4 + 20, 0x404040);
        fontRenderer.drawString(downvotes, width / 2 -30, height / 4 + 30, 0x404040);
        fontRenderer.drawSplitString(reason, width / 2 -84, height / 4 + 50, 250, 0x404040);
        
        super.drawScreen(i, j, f);
        
        xSize_lo = i;
        ySize_lo = j;
    }

	private void drawBackgroundContainer(float f, int i, int j) 
	{
		 //int k = mc.renderEngine.getTexture("/gui/playerinfo.png");
	     //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	     //mc.renderEngine.bindTexture(k);
	     //int l = (width - 176) / 2;
	     //int i1 = (height - 166) / 2;
	     //drawRect(width / 4 - 0, height / 4 - 22, width / 4 + 100, height / 4  + 100, 0x404040);
	     drawBorderedRect(width / 4 + 18, height / 4 - 25, width / 4 + 195, height / 4 + 150, 2, 0xffC2C2C2, 0xaa666666);
	     //drawTexturedModalRect(l, i1, 0, 0, 176, 166);
	     //GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	     //GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
	     //GL11.glPushMatrix();
	     //GL11.glTranslatef(l + 51, i1 + 75, 50F);
	     //float f1 = 30F;
	     //GL11.glScalef(-f1, f1, f1);
	     //GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
	     //GL11.glPopMatrix();
	     //RenderHelper.disableStandardItemLighting();
	     //GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	}
	
	public void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) {
        drawRect(x, y, x1, y1, borderC);
        drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
          } 
}
