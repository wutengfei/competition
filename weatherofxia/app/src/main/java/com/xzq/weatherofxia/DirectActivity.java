package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by lenovo on 2017/11/28.
 */

public class DirectActivity extends Activity {
    private EditText et_cityname;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dierct);
        username = getIntent().getStringExtra("username");
    }

    /**
     * 点击按钮，直接查询输入的城市天气信息
     * @param view
     */
    public void dquery(View view){
        et_cityname = (EditText) findViewById(R.id.et_cityname);
        String cityname = et_cityname.getText().toString();
        DirectActivity.this.finish();
        Intent intent = new Intent(DirectActivity.this,fMainActivity.class);
        intent.putExtra("city",cityname);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode,KeyEvent event){//返回重新启动MainActivity
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            DirectActivity.this.finish();
            Intent intent = new Intent(DirectActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
