package org.frantictools.franticapi;

import java.util.Date;
import java.util.Map;

public class FMPlayer
{
	public Integer id;
	public String username;
	public String group;
	public Date registered;
	public Boolean confirmed;
	public String country;
	public Boolean forumBanned;
	public String timezone;
	public Date lastSeen;
	public Integer health;
	
	public FMPlayer(Integer id, String username, String group, Date registered, Boolean confirmed, String country, Boolean forumBanned, String timezone, Date lastSeen, Integer health)
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
	
	public FMPlayer(Map map)
	{
		id = (Integer) map.get("id");
		username = (String) map.get("username");
		group = (String) map.get("group");
		registered = new Date( (new Long((Integer) map.get("datereg"))) * 1000 );
		confirmed = false;/*(boolean) map.get("confirmed");*/
		country = (String) map.get("country");
		forumBanned = false /*(boolean) map.get("forum_banned")*/;
		timezone = (String) map.get("timezone");
		lastSeen = new Date(2);/*new Date( ((int) map.get("last_time_seen")) * 1000);*/
		health = (Integer) map.get("health");
	}
}
