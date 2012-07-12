package org.frantictools.franticmod;

import java.io.IOException;
import java.lang.reflect.Field;


import org.frantictools.franticmod.gui.GuiFModMenu;
import org.frantictools.franticmod.gui.GuiLogin;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiControls;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Packet;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Session;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.mod_FranticMod;

@SuppressWarnings("unused")
public class FranticMod {

	public Minecraft mc = ModLoader.getMinecraftInstance();
	private mod_FranticMod fMod;
	
	public boolean loggedIn;
	public volatile boolean loggingIn;
	
	private KeyBinding keyBindOpenMenu = new KeyBinding("key.fmodmenu", Keyboard.KEY_BACK);

	public FranticMod(mod_FranticMod mod) {
		this.fMod = mod;
	}

	public void Initialize() {
		ModLoader.registerKey(fMod, keyBindOpenMenu, false);

		ModLoader.addLocalization("key.fmodmenu", "Open FMod Menu");
		
	}

	public void processTick(float tickNumber, Minecraft callerInstance) {
	}
	
	public void processGuiTick(float f, Minecraft minecraft, GuiScreen guiscreen) {

	}
	
	public void processKeybind(KeyBinding event) {
		if (event == this.keyBindOpenMenu) {
			if (loggedIn)
				ModLoader.openGUI(mc.thePlayer, new GuiFModMenu());
			else
				ModLoader.openGUI(mc.thePlayer, new GuiLogin());
		}
	}

}
