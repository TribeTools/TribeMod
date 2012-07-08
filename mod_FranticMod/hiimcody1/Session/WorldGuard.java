package hiimcody1.Session;

import java.util.HashMap;
import hiimcody1.Util.WorldGuardParser;

public class WorldGuard {
	private static HashMap<Integer,WorldGuardParser> wgData = new HashMap<Integer,WorldGuardParser>();
	
	public static int rows = 0;
	
	public static WorldGuardParser getWgData(int wgEntry) {
		return wgData.get(wgEntry);
	}
	
	public static void putWgData(int wgEntryNumber, WorldGuardParser wgData) {
		WorldGuard.wgData.put(wgEntryNumber,wgData);
	}
}
