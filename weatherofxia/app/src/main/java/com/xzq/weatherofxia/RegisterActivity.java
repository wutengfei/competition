package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xzq.weatherofxia.db.MyHelper;

/**
 * Created by lenovo on 2017/11/25.
 */

public class RegisterActivity extends Activity {
    private MyHelper myhelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        myhelper=new MyHelper(this);
    }

    /**
     * 注册按钮，点击进行注册
     * @param view
     */
    public void register(View view){
        EditText et_username= (EditText) findViewById(R.id.et_username);
        EditText et_password= (EditText) findViewById(R.id.et_password);
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        if(Check(username)){//检查是否注册过
            Toast.makeText(this,"该用户名已被注册，注册失败",Toast.LENGTH_SHORT).show();
        }
        else{
            if(Sign(username,password)){//插入数据
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        }
    }

    /**
     * 检查是否重名
     * @param username 输入的用户名
     * @return true or false
     */
    public boolean Check(String username){//检测是否被注册过
        SQLiteDatabase db=myhelper.getWritableDatabase();
        String Query = "Select * from users where username =?";//SQLITE查询语言
        Cursor cursor = db.rawQuery(Query,new String[] { username });//查询名字
        if (cursor.getCount()>0){//注册过
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;//没注册过
    }

    /**
     * 向数据库插入用户名和密码
     * @param username 用户名
     * @param password 密码
     * @return true
     */
    public boolean Sign(String username,String password){//向数据库插入数据
        SQLiteDatabase db= myhelper.getWritableDatabase();
        ContentValues values=new ContentValues();//插入用户名密码
        values.put("username",username);
        values.put("password",password);
        db.insert("users",null,values);//按数据库名字插入
        db.close();
        return true;
    }
}
