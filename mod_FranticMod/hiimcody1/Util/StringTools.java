package hiimcody1.Util;

import hiimcody1.Session.Temp;

public class StringTools {
	public static void storeStringWithConfirm(String message) {
		Temp.stringData.put("confirm", message);
		GuiTools.localMessage(ChatColors.Red+"'"+ChatColors.Gray+message+ChatColors.Red+"' appears to be a mistyping...");
		GuiTools.confirmMessage("Are you sure you wish to sent it?");
	}
	
	public static int rankNameToRank(String name) {
		if (name.equals("Guest"))
			return 0;
		if (name.equals("Everyone"))
			return 1;
		if (name.equals("Regulars"))
			return 2;
		if (name.equals("Donors"))
			return 3;
		if (name.equals("Donorators"))
			return 4;
		if (name.equals("Mayors"))
			return 5;
		if (name.equals("Baby_Moderators"))
			return 6;
		if (name.equals("Moderators"))
			return 7;
		if (name.equals("Head_Moderators"))
			return 8;
		if (name.equals("Administrators"))
			return 9;

		return 0;
	}
	
	public static String colorName(Integer rank, String x) {
		//int rank = rankNameToRank(((HashMap<String, String>) FranticMod.tempData.get(x)).get("group"));
		/*
		 *
		if (name.equals("Guest"))
			return 0;
		if (name.equals("Everyone"))
			return 1;
		if (name.equals("Regulars"))
			return 2;
		if (name.equals("Donors"))
			return 3;
		if (name.equals("Donorators"))
			return 4;
		if (name.equals("Mayors"))
			return 5;
		if (name.equals("Baby_Moderators"))
			return 6;
		if (name.equals("Moderators"))
			return 7;
		if (name.equals("Head_Moderators"))
			return 8;
		if (name.equals("Administrators"))
			return 9;
		 */
		if(rank == 0)
			return ChatColors.Gray+x;
		if(rank == 1)
			return ChatColors.BrightGreen+x;
		if(rank == 2)
			return ChatColors.Red+x;
		if(rank == 3)
			return ChatColors.Blue+x;
		if(rank == 4)
			return ChatColors.Purple+x;
		if(rank == 5)
			return ChatColors.Pink+x;
		if(rank == 6 || rank == 7)
			return ChatColors.Aqua+x;
		if(rank == 8)
			return ChatColors.Gold+x;
		if(rank == 9)
			return ChatColors.Yellow+x;
		
		return ChatColors.Gray+x;
	}
}
