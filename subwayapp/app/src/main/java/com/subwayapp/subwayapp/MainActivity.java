package com.subwayapp.subwayapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity{
    //private static final int item1 = Menu.FIRST;
    //private static final int item2 = Menu.FIRST + 1;
   // private static final int item3 = Menu.FIRST + 2;
    //private boolean mIsExit;

    private Button btnSetAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        btnSetAlarm = (Button) findViewById(R.id.button_setalarm);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Selectcountry.class);
                startActivity(intent);
            }
        });
        //SQLUtils sqlUtils = new SQLUtils(this,"station_info.db",null,1);

    }
}
