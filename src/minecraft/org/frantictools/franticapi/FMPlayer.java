package org.frantictools.franticapi;

import java.util.Date;
import java.util.Map;

public class FMPlayer
{
	public Integer id;
	public String username;
	public Group group;
	public Date registered;
	public Boolean confirmed;
	public String country;
	public Boolean forumBanned;
	public String timezone;
	public Date lastSeen;
	public Integer health;
	
	public FMPlayer(Integer id, String username, Group group, Date registered, Boolean confirmed, String country, Boolean forumBanned, String timezone, Date lastSeen, Integer health)
	{
		this.id = id;
		this.username = username;
		this.group = group;
		this.registered = registered;
		this.confirmed = confirmed;
		this.country = country;
		this.forumBanned = forumBanned;
		this.timezone = timezone;
		this.lastSeen = lastSeen;
		this.health = health;
	}
	
	public static FMPlayer fromMap(Map map)
	{
		return null;
	}
}
