package com.xzq.weatherofxia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2017/11/25.
 */

public class MyHelper extends SQLiteOpenHelper {
    private Context ctt;
    public MyHelper(Context context){
        super(context,"users",null,1);//city是数据库的名字，1为版本
        this.ctt=context;
    }

    /**
     * 创建用户名密码数据库
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users" +
                "(id integer AUTO INCREMENT PRIMARY KEY," +
                "username VARCHAR(20) NOT NULL," +
                "password  VARCHAR(20) NOT NULL)");//建立数据库users
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
