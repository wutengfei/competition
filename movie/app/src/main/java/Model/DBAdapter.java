package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geey on 2017/7/14.
 */
public class DBAdapter {
	
	private static final String DB_NAME = "ChatManage.db";//数据库名称
	private static final String DB_TABLE = "Chat";//表名称
	private static final int DB_version = 1;//数据库版本号
	//表中要创建的字段
	private static final String KEY_ID = "_id";
	private static final String KEY_NAME = "username";
	private static final String KEY_CHAT = "chatrecord";
	
	public SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;
	
	public void beginTransaction () {
		db.beginTransaction();
	}
	
	public void endTransaction () {
		db.endTransaction();
	}
	
	public void setTrasactionSuccessful () {
		db.setTransactionSuccessful();
	}
	
	private static class DBOpenHelper extends SQLiteOpenHelper {
		public DBOpenHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		private static final String DB_CREATE = "Create table " +
				DB_TABLE + "(" +
				KEY_ID + " integer primary key autoincrement," +//注意：字符串连接时要用空格来分开
				KEY_NAME + " varchar(20)," +
				KEY_CHAT + " varchar(50)" +
				")";
		
		@Override
		public void onCreate (SQLiteDatabase _db) {
			_db.execSQL(DB_CREATE);
			System.out.println("创建数据库成功");
			System.out.println("创建数据库成功");
			System.out.println("创建数据库成功");
		}
		
		@Override
		public void onUpgrade (SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(_db);
		}
		
	}
	
	public DBAdapter (Context _context) {
		context = _context;
	}
	
	//打开数据库
	public void open () throws SQLiteException {
		dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_version);
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}
	
	public void close () {
		if (db != null) {
			db.close();
			db = null;
		}
	}
	
	public long InsertChatRecord (ChatRecord chatRecord) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, chatRecord.getUsername());
		newValues.put(KEY_CHAT, chatRecord.getChat());
		return db.insert(DB_TABLE, null, newValues);
	}
	
	public long deleteAll () {
		return (db.delete(DB_TABLE, null, null));
	}
	
	public ChatRecord[] queryChatRecord (String no) {
		Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_CHAT},
				KEY_NAME + " like ? ", new String[]{no}, null, null, null, null);
		return ConvertToChatRecord(cursor);
	}
	
	public ChatRecord[] getAllChatRecord () {
		Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_CHAT}, null, null, null, null, null);
		return ConvertToChatRecord(cursor);
	}
	
	private ChatRecord[] ConvertToChatRecord (Cursor cursor) {
		int resultCouunts = cursor.getCount();//@return the current cursor position
		if (resultCouunts == 0 || !cursor.moveToFirst()) return null;
		ChatRecord[] chatRecords = new ChatRecord[resultCouunts];
		for (int i = 0; i < resultCouunts; i++) {
			chatRecords[i] = new ChatRecord();
			chatRecords[i].setUsername(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			chatRecords[i].setChat(cursor.getString(cursor.getColumnIndex(KEY_CHAT)));
			cursor.moveToNext();
		}
		return chatRecords;
	}
}
