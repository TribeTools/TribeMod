package org.frantictools.franticmod.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.Slot;

public class GuiPlayerMenu extends GuiScreen
{
	private int x, y;
	public GuiPlayerMenu(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void initGui()
	{
		
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		// TODO Auto-generated method stub
		super.drawScreen(par1, par2, par3);
	}
}
