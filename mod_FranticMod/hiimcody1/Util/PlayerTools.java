package hiimcody1.Util;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NetClientHandler;

public class PlayerTools {
	public static boolean playerIsOnline(String playerName) {
		NetClientHandler netclienthandler = ((EntityClientPlayerMP)ModLoader.getMinecraftInstance().thePlayer).sendQueue;
        java.util.List<?> list = netclienthandler.playerInfoList;
        int sz = list.size();
        int i = 0;
        while(i < sz) {
        	if(((GuiPlayerInfo) list.get(i)).name.equalsIgnoreCase(playerName)) {
        		return true;
        	}
        	i++;
        }
        return false;
	}
}
