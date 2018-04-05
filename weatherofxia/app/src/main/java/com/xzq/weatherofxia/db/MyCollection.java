package com.xzq.weatherofxia.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import com.xzq.weatherofxia.bean.myCollection;

/**
 * Created by lenovo on 2017/11/26.
 */

public class MyCollection extends SQLiteOpenHelper {
    private Context ctt;
    public MyCollection(Context context){
        super(context,"collection",null,1);//city是数据库的名字，1为版本
        this.ctt=context;
    }

    /**
     * 创建数据库，储存收藏信息
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE collection" +
                "(id integer AUTO INCREMENT PRIMARY KEY," +
                "username VARCHAR(20) NOT NULL," +
                "cityname  VARCHAR(20) NOT NULL)");//建立数据库users
    }

    /**
     * 根据用户名查询，该用户的收藏信息
     * @param username 用户名
     * @return ArrayList<myCollection> 符合条件的信息
     */
    public ArrayList<myCollection> queryCityname(String username){//根据parent_id查询的方法
        SQLiteDatabase db =getReadableDatabase();//获取只读数据库
        Cursor cs =db.query("collection",null,"username=?",new String[]{username+""},null,null,null);
        //cs是游标，类似指针的作用,1,表明，2,null代表全查询，3查询条件为Parent_id，4，为3中？代表的内容
        ArrayList<myCollection> myCollections =new ArrayList<myCollection>();
        while (cs.moveToNext()){//游标，往下查
            myCollection myCollection =new myCollection();
            myCollection.id=cs.getInt(cs.getColumnIndex("id"));//获取值
            myCollection.username=username;
            myCollection.cityname=cs.getString(cs.getColumnIndex("cityname"));
            myCollections.add(myCollection);
        }
        cs.close();
        db.close();
        return myCollections;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
