package Control;

import android.content.Context;

import Model.ChatRecord;
import Model.ChatRecordSet;
import Model.DBAdapter;


/**
 * Created by Geey on 2017/10/6.
 */

public class ChatRecordControl {
	private static DBAdapter dbAdapter;
	private static ChatRecordSet chatlist;
	private Context context;
	
	public ChatRecordControl(Context context) {
		this.context = context;
		chatlist = ChatRecordSet.getChatList();
		dbAdapter = new DBAdapter(context);
		dbAdapter.open();
	}
	
	public boolean addChatRecord(ChatRecord chatRecord) {
		dbAdapter.InsertChatRecord(chatRecord);
		return true;
	}
	public ChatRecord[] getAllChatRecord() {
		return dbAdapter.getAllChatRecord();
	}
	
	public ChatRecord[] queryUername(String info) {
		return dbAdapter.queryChatRecord(info);
	}
	
	
	public boolean Delete_All_ChatRecord() {
		dbAdapter.deleteAll();
		return true;
	}
}
