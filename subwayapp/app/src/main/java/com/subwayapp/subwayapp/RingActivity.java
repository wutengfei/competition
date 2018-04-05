package com.subwayapp.subwayapp;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.one);
        mediaPlayer.start();
        vibrator.vibrate(new long[]{1000,100,100,100,100}, 0);
        findViewById(R.id.btnCancelAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(v);
            }
        });
    }

    public void stop(View view){
        mediaPlayer.stop();
        vibrator.cancel();
        Alarm.instance.finish();
        finish();
    }
}
