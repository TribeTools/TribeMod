package net.minecraft.FranticMod.Util;

public class ChatColors {
	    public static String Black = "\2470";
	    public static String DarkBlue = "\2471";
	    public static String DarkGreen = "\2472";
	    public static String Teal = "\2473";
	    public static String DarkRed = "\2474";
	    public static String Purple = "\2475";
	    public static String Gold = "\2476";
	    public static String Gray = "\2477";
	    public static String DarkGray = "\2478";
	    public static String Blue = "\2479";
	    public static String BrightGreen = "\247a";
	    public static String Aqua = "\247b";
	    public static String Red = "\247c";
	    public static String Pink = "\247d";
	    public static String Yellow = "\247e";
	    public static String White = "\247f";
	    
		public static String stripColors(String string) {
			return string.replace(ChatColors.Aqua, "").replace(ChatColors.Black, "").replace(ChatColors.Blue, "").replace(ChatColors.BrightGreen, "").replace(ChatColors.DarkBlue, "").replace(ChatColors.DarkGray, "").replace(ChatColors.DarkGreen, "").replace(ChatColors.DarkRed, "").replace(ChatColors.Gold, "").replace(ChatColors.Gray, "").replace(ChatColors.Pink, "").replace(ChatColors.Purple, "").replace(ChatColors.Red, "").replace(ChatColors.Teal, "").replace(ChatColors.White, "").replace(ChatColors.Yellow, "");
		}
		
		public static String replaceColors(String colorToReplace, String colorToReplaceWith, String sourceString) {
			return sourceString.replace(colorToReplace, colorToReplaceWith);
		}
}
