package com.subwayapp.subwayapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RingReceived extends BroadcastReceiver {
    public RingReceived() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if("com.pxd.alarmandnotice.RING".equals(intent.getAction())){
            Log.i("test","闹钟响了");
            //跳转到Activity n //广播接受者中（跳转Activity）
            Intent intent1=new Intent(context,RingActivity.class);
            //给Intent设置标志位
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
