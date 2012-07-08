package hiimcody1.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



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
	    
	    public static HashMap<String,String> getColor = new HashMap<String,String>();
	    
	    public static void init() {
	    	getColor.put("Black", Black);
	    	getColor.put("DarkBlue", DarkBlue);
	    	getColor.put("DarkGreen", DarkGreen);
	    	getColor.put("Teal", Teal);
	    	getColor.put("DarkRed", DarkRed);
	    	getColor.put("Purple", Purple);
	    	getColor.put("Gold", Gold);
	    	getColor.put("Gray", Gray);
	    	getColor.put("DarkGray", DarkGray);
	    	getColor.put("Blue", Blue);
	    	getColor.put("BrightGreen", BrightGreen);
	    	getColor.put("Aqua", Aqua);
	    	getColor.put("Red", Red);
	    	getColor.put("Pink", Pink);
	    	getColor.put("Yellow", Yellow);
	    	getColor.put("White", White);
	    	
	    	getColor.put("0", "Black");
	    	getColor.put("1", "DarkBlue");
	    	getColor.put("2", "DarkGreen");
	    	getColor.put("3", "Teal");
	    	getColor.put("4", "DarkRed");
	    	getColor.put("5", "Purple");
	    	getColor.put("6", "Gold");
	    	getColor.put("7", "Gray");
	    	getColor.put("8", "DarkGray");
	    	getColor.put("9", "Blue");
	    	getColor.put("a", "BrightGreen");
	    	getColor.put("b", "Aqua");
	    	getColor.put("c", "Red");
	    	getColor.put("d", "Pink");
	    	getColor.put("e", "Yellow");
	    	getColor.put("f", "White");
	    }
	    
		public static String stripColors(String string) {
			return string.replace(ChatColors.Aqua, "").replace(ChatColors.Black, "").replace(ChatColors.Blue, "").replace(ChatColors.BrightGreen, "").replace(ChatColors.DarkBlue, "").replace(ChatColors.DarkGray, "").replace(ChatColors.DarkGreen, "").replace(ChatColors.DarkRed, "").replace(ChatColors.Gold, "").replace(ChatColors.Gray, "").replace(ChatColors.Pink, "").replace(ChatColors.Purple, "").replace(ChatColors.Red, "").replace(ChatColors.Teal, "").replace(ChatColors.White, "").replace(ChatColors.Yellow, "");
		}
		
		public static String replaceColors(String colorToReplace, String colorToReplaceWith, String sourceString) {
			return sourceString.replace(colorToReplace, colorToReplaceWith);
		}
}
