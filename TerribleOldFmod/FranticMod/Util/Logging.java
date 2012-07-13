package net.minecraft.FranticMod.Util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Logging {
	private Logger logger = Logger.getLogger("FMod");
	
	public Logging() {
		//try {
			//FileHandler hand = new FileHandler("FMod.log");
			//
			//logger.addHandler(hand);
			
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}
	
	public void info(String message) {
		//logger.info(message);
		temp(message);
	}
	
	public void warn(String message) {
		//logger.warning(message);
		temp(message);
	}
	
	public void severe(String message) {
		//logger.severe(message);
		temp(message);
	}
	
	public void temp(String message) {
		System.out.println("[FMOD] "+message);
	}
	
}
