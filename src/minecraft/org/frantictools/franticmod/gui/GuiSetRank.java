package org.frantictools.franticmod.gui;

import java.text.SimpleDateFormat;

import org.frantictools.franticapi.FMPlayer;
import org.lwjgl.input.Keyboard;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class GuiSetRank extends GuiScreen
{
	private GuiScreen parentGuiScreen;
	
	private float xSize_lo;
	private float ySize_lo;

	private String name;
	private String curRank;
	
	@SuppressWarnings("unchecked")
	public GuiSetRank(FMPlayer player, GuiScreen parent)
	{	
		this.name 	= 	player.username;
		this.curRank 	= 	player.group;
		this.parentGuiScreen = parent;
	}
	
	@Override
	public void updateScreen()
	{
//		((GuiButton)controlList.get(0)).enabled = mod_FranticMod.rank > 5;
//		((GuiButton)controlList.get(1)).enabled = mod_FranticMod.rank > 5;
//		((GuiButton)controlList.get(2)).enabled = mod_FranticMod.rank > 5;
//		((GuiButton)controlList.get(3)).enabled = mod_FranticMod.rank > 5;
//		((GuiButton)controlList.get(7)).enabled = mod_FranticMod.rank > 6;
//		((GuiButton)controlList.get(5)).enabled = mod_FranticMod.rank > 7;
//		((GuiButton)controlList.get(6)).enabled = mod_FranticMod.rank > 7;
//		
//		if(player.equalsIgnoreCase(mc.session.username) || player.equalsIgnoreCase(mod_FranticMod.loginName)) {
//			((GuiButton)controlList.get(0)).enabled = false; //Everyone
//			((GuiButton)controlList.get(1)).enabled = false; //Donor
//			((GuiButton)controlList.get(2)).enabled = false; //Donorator
//			((GuiButton)controlList.get(3)).enabled = false; //Regular
//			((GuiButton)controlList.get(7)).enabled = false; //SuperStalk
//			((GuiButton)controlList.get(5)).enabled = false; //Baby
//			((GuiButton)controlList.get(6)).enabled = false; //Mayor
//		}
//		if(!mod_FranticMod.playerIsOnline(player) || !mod_FranticMod.isIntegratedForSuperStalk()) {
//			((GuiButton)controlList.get(7)).enabled = false;
//		}
//		
//		if(!mod_FranticMod.isIntegratedForSuperStalk())
//			((GuiButton)controlList.get(7)).xPosition = -9000;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		int i = height / 4 + 50;
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 84, height / 2 - 82, 60, 20, "« Back"));
		controlList.add(new GuiButton(1, width / 2 - (150 / 2), i, 150, 20, "Trustee"));
		controlList.add(new GuiButton(2, width / 2 - (150 / 2), i + 24, 150, 20, "Devotee"));
		controlList.add(new GuiButton(3, width / 2 - (150 / 2), i + 48, 150, 20, "Patron"));
		controlList.add(new GuiButton(4, width / 2 - (150 / 2), i + 72, 150, 20, "Noble"));
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
		
		fontRenderer.drawString("Set Rank:", width / 2 - 84, height / 4 + 40, 0xffffff);
		
		
		fontRenderer.drawString("Username:", width / 2 - 84, height / 4, 0xffffff);
		fontRenderer.drawString("Rank:", width / 2 - 84, height / 4 + 10, 0xffffff);
		
		fontRenderer.drawString(name, width / 2 -30, height / 4, 0xffffff);
		fontRenderer.drawString(curRank, width / 2 -30, height / 4 + 10, 0xffffff);
		
		super.drawScreen(i, j, f);
		
		xSize_lo = i;
		ySize_lo = j;
	}
}
