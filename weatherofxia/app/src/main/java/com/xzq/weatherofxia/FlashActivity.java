package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by lenovo on 2017/11/23.
 * 启动页
 */

public class FlashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash);
        Handler x = new Handler();//Handler方法，用来延时
        x.postDelayed(r, 2000);//2秒后进入r线程
    }

    /**
     * 用来启动LoginActivity的线程
     */
 Runnable r=new Runnable() {
     @Override
     public void run() {
         Intent intent=new Intent(FlashActivity.this,com.xzq.weatherofxia.LoginActivity.class);
         startActivity(intent);//打开LoginActivity
         FlashActivity.this.finish();//结束启动页
     }
 };
}