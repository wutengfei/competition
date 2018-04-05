package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xzq.weatherofxia.db.MyHelper;

/**
 * Created by lenovo on 2017/11/25.
 */

public class LoginActivity extends Activity{
    private MyHelper myhelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        myhelper=new MyHelper(this);
    }

    /**
     * 点击登录按钮触发是否登录的事件
     * @return 无
     * @param view
     */
    public void login(View view){
        EditText userName= (EditText) findViewById(R.id.et_1);
        EditText passWord= (EditText) findViewById(R.id.et_2);
        String username=userName.getText().toString();
        String password=passWord.getText().toString();
        if (loginGo(username,password)) {
            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            LoginActivity.this.finish();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册按钮，打开注册activity
     * @param view
     */
    public void register(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     *判断用户名密码是否正确
     * @param username 用户名
     * @param password 密码
     * @return true or false
     */
    public boolean loginGo(String username,String password){
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String sql = "select * from users where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
