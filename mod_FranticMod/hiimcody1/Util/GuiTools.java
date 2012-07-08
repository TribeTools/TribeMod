package hiimcody1.Util;

import hiimcody1.FranticMod;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Achievement;
import net.minecraft.src.Block;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiButton;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class GuiTools {
	public static GuiButton getButtonByDisplayString(List<?> controlList, String displayString) {
		for (int i = 0; i < controlList.size(); i++) {
			if(controlList.get(i) instanceof GuiButton) {
				if(((GuiButton) controlList.get(i)).displayString.equalsIgnoreCase(displayString))
					return ((GuiButton) controlList.get(i));
			}
		}
		return null;
	}
	
	public static GuiButton getButtonById(List<?> controlList, int id) {
		for (int i = 0; i < controlList.size(); i++) {
			if(controlList.get(i) instanceof GuiButton) {
				if(((GuiButton) controlList.get(i)).id == id)
					return ((GuiButton) controlList.get(i));
			}
		}
		return null;
	}
	
	public static void showToastMessage(String title, String message) {
		showAchievementInfo(title, message, new ItemStack(Block.signWall));
	}
	
	@SuppressWarnings("unused")
	private static void showAchievementInfo(final String caption, final String text) {
		showAchievementInfo(caption, text, new ItemStack(Block.workbench));
	}

	private static void showAchievementInfo(String caption, String text, ItemStack item) {
		try {
			GuiAchievement gui = ModLoader.getMinecraftInstance().guiAchievement;
			gui.queueTakenAchievement(new Achievement(100, "", 0, 0, item, null));
			ModLoader.setPrivateValue(GuiAchievement.class, gui, 3, caption);
			ModLoader.setPrivateValue(GuiAchievement.class, gui, 4, text);
		} catch (Exception e) {
		}
	}

	public static void showModMessage(String title, String text, int itemId) {
		showAchievementInfo(title, text, new ItemStack(itemId, 0, 0));
	}
	
	public static void showModMessage(String title, String text, Block block) {
		showModMessage(title, text, block.blockID);
	}
	
	public static void drawOutlineString(String s, int i, int j, int k, int l) {
		//Method renderStringMethod = FontRenderer.class.getDeclaredMethod("renderString", null);
		//renderStringMethod.setAccessible(true);
		Minecraft mc = ModLoader.getMinecraftInstance();
		mc.fontRenderer.drawString(s, i - 1, j, l);
		mc.fontRenderer.drawString(s, i + 1, j, l);
		mc.fontRenderer.drawString(s, i, j - 1, l);
		mc.fontRenderer.drawString(s, i, j + 1, l);
		mc.fontRenderer.drawString(s, i, j, k);
	}
	
	public static void drawString(String s, int i, int j, int k) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		mc.fontRenderer.drawStringWithShadow(s, i, j, k);
	}
	
	public static boolean isIdle() {
		if(ModLoader.getMinecraftInstance().currentScreen == null)
			return true;
		else
			return false;
	}
	
	public static void localMessage(String message) {
		ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(message);
	}
	
	public static void ircLocalMessage(String message) {
		localMessage(ChatColors.getColor.get(Config.props.getProperty("ircColor", "Purple"))+message);
	}
	
	public static void confirmMessage(String message) {
		localMessage(ChatColors.Gray+message);
		localMessage(ChatColors.Gray+"/yes or /no");
		FranticMod.pendingConfirm=true;
	}
}
