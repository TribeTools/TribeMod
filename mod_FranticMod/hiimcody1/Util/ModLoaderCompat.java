package hiimcody1.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
@SuppressWarnings("unused")
public class ModLoaderCompat {
	public Minecraft mc = ModLoader.getMinecraftInstance();
	
	public void showScreen(EntityPlayer player, GuiScreen screen) {
		ModLoader.openGUI(player, screen);
	}
	
	public void isScreenOpen(GuiScreen screen) {
		ModLoader.isGUIOpen(screen.getClass());
	}
	
	public void mapKey(BaseMod mod, KeyBinding keyBind, boolean b) {
			ModLoader.registerKey(mod, keyBind, b);
	}
	
	public void addLocalization(String string, String string2) {
			ModLoader.addLocalization(string, string2);
	}
	
}
