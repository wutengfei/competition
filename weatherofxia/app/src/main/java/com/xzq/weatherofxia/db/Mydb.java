package com.xzq.weatherofxia.db;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xzq.weatherofxia.bean.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by lenovo on 2017/11/20.
 */

public class Mydb extends SQLiteOpenHelper {
    private Context ctt;
    public Mydb(Context context){
        super(context,"city",null,1);//city是数据库的名字，1为版本
        this.ctt=context;
    }

    /**
     * 创建省市数据库并插入值
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE swf_area" +
                "(id integer AUTO INCREMENT PRIMARY KEY," +
                "parent_id integer NOT NULL DEFAULT 0," +
                "name VARCHAR(50) NOT NULL," +
                "sort integer NOT NULL DEFAULT 0)");//建立数据库
        insertCitys(db);//读取db
    }

    /**
     * 打开sql文件并读取其中的语句，并按语句像省市数据库插入信息
     * @param db 数据库
     */
    private void insertCitys(SQLiteDatabase db) {
        try{
            String line;
            InputStream is =((Activity)ctt).getAssets().open("sql");
            //InputStream为字节流，可以读取任何语句，打开sql
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //BufferedReader为字符流，只能读字符
            while ((line=br.readLine())!=null){//逐行读取
                line=line.trim();//删除收尾空格
                if(line.length()>0){
                    db.execSQL(line);//不空则插入数据
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的parent_id查符合条件的数据
     * @param parent_id swf_area中的一个列
     * @return citys 符合条件的城市
     */
  public ArrayList<City> queryCity(int parent_id){//根据parent_id查询的方法
        SQLiteDatabase db =getReadableDatabase();//获取只读数据库
        Cursor cs =db.query("swf_area",null,"parent_id=?",new String[]{parent_id+""},null,null,null);
        //cs是游标，类似指针的作用,1,表明，2,null代表全查询，3查询条件为Parent_id，4，为3中？代表的内容
        ArrayList<City> citys=new ArrayList<City>();
        while (cs.moveToNext()){//游标，往下查
            City city =new City();
            city.id=cs.getInt(cs.getColumnIndex("id"));//获取值
            city.name=cs.getString(cs.getColumnIndex("name"));
            city.parent_id=parent_id;
            city.sort=cs.getInt(cs.getColumnIndex("sort"));
            citys.add(city);
        }
        cs.close();
        db.close();
        return citys;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
