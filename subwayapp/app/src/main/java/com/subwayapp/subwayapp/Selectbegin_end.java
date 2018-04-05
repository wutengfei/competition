package com.subwayapp.subwayapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import com.subwayapp.subwayapp.utils.SQLUtils;

/**
 * Created by 小元 on 2017/11/18.
 */

public class Selectbegin_end extends AppCompatActivity {


    private SQLUtils sqlUtils;
    private int line;
    private Spinner  sp;
    private String beginStation;
    private Spinner  sp2;
    private String endStation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectbegin_end);
        sqlUtils = new SQLUtils(this,"station_info.db",null,1);
        line = getIntent().getIntExtra("line", 1);
        sp = (Spinner) findViewById(R.id.spinner_begin);
        beginStation = (String) sp.getSelectedItem();
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                beginStation = (String) sp.getSelectedItem();
                //把该值传给 TextView
                //tv.setText(beginStation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        sp2 = (Spinner) findViewById(R.id.spinner_end);
        //tv2 = (TextView) findViewById(R.id.txt02);
        endStation = (String) sp2.getSelectedItem();
        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                //拿到被选择项的值
                endStation = (String) sp2.getSelectedItem();
                //把该值传给 TextView
                //tv2.setText(endStation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int beginTime = sqlUtils.getIntervalTime(beginStation, line);
                int endTime = sqlUtils.getIntervalTime(endStation, line);
                int intervalTime = Math.abs(beginTime-endTime);
                Log.e("selectbegin_end间隔时间", ":"+intervalTime);
                Intent intentGo = new Intent();
                intentGo.putExtra("intervalTime", intervalTime);
                intentGo.setClass(Selectbegin_end.this, Alarm.class);
                startActivity(intentGo);
            }
        });
    }
}