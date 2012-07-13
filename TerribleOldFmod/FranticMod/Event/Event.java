package net.minecraft.FranticMod.Event;

public abstract class Event {
	boolean isCancelled = false;
	
	public boolean isCancelled() {
		return isCancelled;
	}
	
	public void execute() {
		
	}
}
