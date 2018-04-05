package Model;

/**
 * Created by Geey on 2017/10/6.
 */

public class ChatRecord {
	private String Username;
	private String Chat;//聊天内容
	
	public ChatRecord(){
		
	}
	public ChatRecord(String Username,String Chat){
		this.Username=Username;
		this.Chat=Chat;
	}
	public void setChat (String chat) {
		Chat = chat;
	}
	
	public String getChat () {
		return Chat;
	}
	
	public void setUsername (String username) {
		Username = username;
	}
	
	public String getUsername () {
		return Username;
	}
}
