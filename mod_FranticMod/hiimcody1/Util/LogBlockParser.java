package hiimcody1.Util;

public class LogBlockParser {
	private String chatMessage;
	
	public LogBlockParser(String chatMessage) {
		this.chatMessage = chatMessage;
	}
	
	public String getPlayer() {
		String[] raw = chatMessage.split(" ");
		return raw[1].replace(ChatColors.Pink, "");
	}
	
	public String getAction() {
		String[] raw = chatMessage.split(" ");
		return raw[2].replace(ChatColors.Gold, "");
	}
	
	public Integer getItem() {
		String[] raw = chatMessage.split(ChatColors.White);
		String[] raw2= raw[1].split("\\(#");
		return Integer.parseInt(raw2[1].replace(").", ""));
	}
	
	public boolean isLogBlock() {
		if(chatMessage.startsWith(ChatColors.Teal+"Block changes"))
			return true;
		else
			return false;
	}
}
