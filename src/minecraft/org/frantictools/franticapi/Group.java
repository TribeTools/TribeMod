package org.frantictools.franticapi;

public enum Group
{
	GUEST,
	MEMBER,
	TRUSTEE,
	DEVOTEE,
	PATRON,
	NOBLE,
	PEACEKEEPER,
	COUNCIL,
	OWNER;
	
	public static Group fromString(String x)
	{
//		switch(x)
//		{
//		case "Guest": return GUEST;
//		case "Memeber": return MEMBER;
//		case "Trustee": return TRUSTEE;
//		case "Devote": return DEVOTEE;
//		case "Patron": return PATRON;
//		case "Noble": return NOBLE;
//		case "Peacekeeper": return PEACEKEEPER;
//		case "Council": return COUNCIL;
//		case "Owner": return OWNER;
//		}
		
		return GUEST;
	}
}
