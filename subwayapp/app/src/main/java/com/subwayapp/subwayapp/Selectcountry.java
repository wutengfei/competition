package com.subwayapp.subwayapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 小元 on 2017/11/18.
 */

public class Selectcountry extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcountry);
        findViewById(R.id.button_beijing).setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.button_beijing) {
            Intent intent = new Intent();
            intent.setClass(this,Selectline.class);
            this.startActivity(intent);
        }
    }
}
