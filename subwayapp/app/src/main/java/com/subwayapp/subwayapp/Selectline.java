package com.subwayapp.subwayapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 小元 on 2017/11/18.
 */

public class Selectline extends AppCompatActivity implements View.OnClickListener {
    private int line;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectline3);
        findViewById(R.id.button_line1).setOnClickListener(this);

    }
    public void onClick(View v) {
   if (v.getId() == R.id.button_line1) {
          line=1;
            Intent intent = new Intent();
            intent.putExtra("line", line);
            intent.setClass(Selectline.this,Selectbegin_end.class);
            this.startActivity(intent);
        }
    }
}
