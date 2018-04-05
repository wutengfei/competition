package com.subwayapp.subwayapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by jing on 17-11-7.
 */

public class SQLUtils extends SQLiteOpenHelper{

    private static final String CREATE_STATION_TABLE = "create table station_info(" +
            "stationId integer primary key autoincrement," +
            "stationName text," +
            "intervalTime integer," +
            "line integer);";
    private Context context;

    public SQLUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
        dropTable();
        initDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_STATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    private void initDatabase(){
        SQLiteDatabase db = getWritableDatabase();
        String data = "insert into station_info(stationName, intervalTime, line) values" +
                "('苹果园',0,1)," +
                "('古城',6,1)," +
                "('八角游乐园',11,1)," +
                "('八宝山',15,1)," +
                "('玉泉路',19,1)," +
                "('五棵松',23,1)," +
                "('万寿路',27,1)," +
                "('公主坟',30,1)," +
                "('军事博物馆',34,1)," +
                "('木樨地',38,1)," +
                "('南礼士路',42,1)," +
                "('复兴门',45,1)," +
                "('西单',49,1)," +
                "('天安门西',52,1)," +
                "('天安门东',55,1)," +
                "('王府井',58,1)," +
                "('东单',61,1)," +
                "('建国门',64,1)," +
                "('永安里',68,1)," +
                "('国贸',72,1)," +
                "('大望站',76,1)," +
                "('四惠',80,1)," +
                "('四惠东',84,1);";

        db.execSQL(data);
    }

    private void dropTable(){
        context.deleteDatabase("station_info.db");
    }

    public Cursor getCursor(String stationName, int line){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery("select intervalTime from station_info where stationName=? and line=?",new String[]{stationName,String.valueOf(line)});
    }

    public int getIntervalTime(String stationName, int line){
        int interval=0;
        Cursor cursor = getCursor(stationName, line);
        Log.e("SQLUtils:", "查询结果游标索引："+cursor.getCount());
        if(cursor.moveToFirst()){

            interval = cursor.getInt(cursor.getColumnIndex("intervalTime"));

        }else{
            Toast.makeText(context,"查找失败",Toast.LENGTH_SHORT).show();
            return -1;
        }

        return interval;
    }

}
