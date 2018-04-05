package Model;

import java.util.ArrayList;

/**
 * Created by Geey on 2017/10/6.
 */

public class ChatRecordSet extends ArrayList<ChatRecord> {
		private static ChatRecordSet Chatlist = null;//定义存储唯一图书集合类的引用变量

    private ChatRecordSet() {//封装构造函数
		}
	
	public static ChatRecordSet getChatList() {//用静态函数生成集合对象
		if (Chatlist == null)
			Chatlist = new ChatRecordSet();
		return Chatlist;
	}
}
