package com.subwayapp.subwayapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    public static Alarm instance = null;
    private int intervalTime;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        instance = this;
        intervalTime = getIntent().getIntExtra("intervalTime", 0);
        Log.e("Alarm", "间隔时间:"+intervalTime);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        Intent intent = new Intent();
        intent.setAction("com.pxd.alarmandnotice.RING");
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, intent, 0);
        if(intervalTime==0)intervalTime=1;
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 60000*(intervalTime-1), pendingIntent);
        Log.e("Alarm", ":闹钟设置成功");
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Log.e("Alarm:", "取消闹钟");
                Toast.makeText(Alarm.this, "闹钟已取消", Toast.LENGTH_SHORT);
                finish();
            }
        });
    }
}
