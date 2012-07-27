package org.frantictools.franticapi;

public enum BanReason
{
	ASSHATTERY(1),
	DRAMA(2),
	CHEATER(3),
	RECRUITING(4),
	LANGUAGE(5);
	
	private BanReason(int id)
	{
		this.id = id;
	}
	
	public final int id; 
}
