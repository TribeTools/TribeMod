package net.minecraft.FranticMod.Gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

public class GuiPlayerInfo extends GuiScreen{
	private GuiScreen parentGuiScreen;
	
	private float xSize_lo;
	private float ySize_lo;

	private String player;
	private String playerId;
	private String rank;
	private String joinDate;
	private String lastBan;
	private String banLevel;
	
	@SuppressWarnings("unchecked")
	public GuiPlayerInfo(String name)
	{
		
		player 		= 	name;
		rank 		= 	((HashMap<String, String>) mod_FranticMod.playerList.get(name)).get("group");
		joinDate 	= 	((HashMap<String, String>) mod_FranticMod.playerList.get(name)).get("regdate");
		playerId 	= 	((HashMap<String, String>) mod_FranticMod.playerList.get(name)).get("id");
		lastBan 	= 	"Not Implemented";
		banLevel 	= 	"Not Implemented";
		
	}
	
	@Override
	public void updateScreen()
	{
		((GuiButton)controlList.get(0)).enabled = mod_FranticMod.rank > 5;
		((GuiButton)controlList.get(1)).enabled = mod_FranticMod.rank > 5;
		((GuiButton)controlList.get(2)).enabled = mod_FranticMod.rank > 5;
		((GuiButton)controlList.get(3)).enabled = mod_FranticMod.rank > 5;
		((GuiButton)controlList.get(7)).enabled = mod_FranticMod.rank > 6;
		((GuiButton)controlList.get(5)).enabled = mod_FranticMod.rank > 7;
		((GuiButton)controlList.get(6)).enabled = mod_FranticMod.rank > 7;
		
		if(player.equalsIgnoreCase(mc.session.username) || player.equalsIgnoreCase(mod_FranticMod.loginName)) {
			((GuiButton)controlList.get(0)).enabled = false; //Everyone
			((GuiButton)controlList.get(1)).enabled = false; //Donor
			((GuiButton)controlList.get(2)).enabled = false; //Donorator
			((GuiButton)controlList.get(3)).enabled = false; //Regular
			((GuiButton)controlList.get(7)).enabled = false; //SuperStalk
			((GuiButton)controlList.get(5)).enabled = false; //Baby
			((GuiButton)controlList.get(6)).enabled = false; //Mayor
		}
		if(!mod_FranticMod.playerIsOnline(player) || !mod_FranticMod.isIntegratedForSuperStalk()) {
			((GuiButton)controlList.get(7)).enabled = false;
		}
		
		if(!mod_FranticMod.isIntegratedForSuperStalk())
			((GuiButton)controlList.get(7)).xPosition = -9000;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 85, height / 4 + 80, 80, 20, "Everyone"));
		controlList.add(new GuiButton(1, width / 2 - 85, height / 4 + 102, 80, 20, "Donor"));
		controlList.add(new GuiButton(2, width / 2 + 4, height / 4 + 102, 80, 20, "Donorator"));
		controlList.add(new GuiButton(3, width / 2 + 4, height / 4 + 80, 80, 20, "Regular"));
		controlList.add(new GuiButton(4, width / 2 + 65, height / 2 - 82, 20, 20, "X"));
		controlList.add(new GuiButton(5, width / 2 - 85, height / 4 + 124, 80, 20, "Baby Mod"));
		controlList.add(new GuiButton(6, width / 2 + 4, height / 4 + 124, 80, 20, "Mayor"));
		controlList.add(new GuiButton(7, width / 2 + 24, height / 4 +58, 60, 20, "SuperStalk"));
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
			try {
				UrlLib.promote(playerId,"2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		if(guibutton.id == 1)
		{
			try {
				UrlLib.promote(playerId,"4");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(guibutton.id == 2)
		{
			try {
				UrlLib.promote(playerId,"5");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(guibutton.id == 3)
		{
			try {
				UrlLib.promote(playerId,"3");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(guibutton.id == 5)
		{
			try {
				UrlLib.promote(playerId,"7");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(guibutton.id == 6)
		{
			try {
				UrlLib.promote(playerId,"6");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(guibutton.id == 4)
		{
			mc.displayGuiScreen(parentGuiScreen);
		}
		if(guibutton.id == 7)
		{
			mod_FranticMod.setupRemoveView(player);
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
		
		fontRenderer.drawString("Set rank:", width / 2 - 84, height / 4 + 70, 0x404040);
		
		fontRenderer.drawStringWithShadow("Player Information", width / 2 - 84, height / 4 - 20, 0x00FFFF);
		
		fontRenderer.drawString("Username:", width / 2 - 84, height / 4, 0x404040);
		fontRenderer.drawString("Rank:", width / 2 - 84, height / 4 + 10, 0x404040);
		fontRenderer.drawString("Joined:", width / 2 - 84, height / 4 + 20, 0x404040);
		fontRenderer.drawString("Last Ban:", width / 2 - 84, height / 4 + 30, 0x404040);
		fontRenderer.drawString("Ban Level:", width / 2 - 84, height / 4 + 40, 0x404040);
		
		if(mod_FranticMod.playerIsOnline(player))
			fontRenderer.drawString(player, width / 2 -30, height / 4, 0x3ADF00);
		else
			fontRenderer.drawString(player, width / 2 -30, height / 4, 0x404040);
		
		fontRenderer.drawString(rank, width / 2 -30, height / 4 + 10, 0x404040);
		fontRenderer.drawString(joinDate, width / 2 -30, height / 4 + 20, 0x404040);
		fontRenderer.drawString(lastBan, width / 2 -30, height / 4 + 30, 0x404040);
		fontRenderer.drawString(banLevel, width / 2 -30, height / 4 + 40, 0x404040);
		
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
