package net.minecraft.src;

import net.minecraft.client.Minecraft;
import hiimcody1.FranticMod;
import hiimcody1.Util.GuiTools;

@SuppressWarnings("unused")
public class mod_FranticMod extends BaseMod {
	private FranticMod fMod = new FranticMod(this);
	
	public static String version = "0.35";

	public mod_FranticMod() {
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void load() {
		fMod.Initialize();
			ModLoader.setInGameHook(this, true, false);
	}
	
	public boolean OnTickInGame(float partialticks, Minecraft mc) {
		return onTickInGame(partialticks, mc);
	}
	
	public boolean OnTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		return onTickInGUI(f,minecraft,guiscreen);
	}
	
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen) {
		//Process everything in the master class
		fMod.processGuiTick(f, minecraft, guiscreen);
		return true;
	}
	
	public boolean onTickInGame(float partialticks, Minecraft mc) {
		//Process everything in the master class
		fMod.processTick(partialticks, mc);
		return true;
	}
	
	public void KeyboardEvent(KeyBinding event)
    {
		keyboardEvent(event);
    }
	
	public void keyboardEvent(KeyBinding event)
    {
		fMod.processKeybind(event);
    }

}
