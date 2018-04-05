package com.example.five_two;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.five_two.UMSGUI;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

//import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static com.example.five_two.R.layout.activity_main;
//public Article();

public class MainActivity extends AppCompatActivity {
    //WebView webviewt = (WebView) findViewById(R.id.webview);;
    //private String url = "http://bmob-cdn-17570.b0.upaiyun.com/2018/03/23/6cb4ec8e40f852cc805ce7fa26903beb.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bmob.initialize(this, "8f1fd8777e9827d1567a0ae2b00d8746");
        //BmobSMS.initialize(this, "8f1fd8777e9827d1567a0ae2b00d8746");
        setContentView(R.layout.activity_main);



        Button one = (Button) this.findViewById(R.id.button_select);
        one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SelectActivity.class));
            }
        });



        Button two = (Button) this.findViewById(R.id.button_exit);
        two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                finish();
                System.exit(0);
            }
        });


        Button one11 = (Button) this.findViewById(R.id.button);
        one11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                UMSGUI.setTheme(com.example.five_two.themes.defaultt.DefaultTheme.class);
                UMSGUI.showProfilePage();
            }
        });

        Button we1 = (Button) this.findViewById(R.id.button2);
        we1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, IntroductionActivity.class));
            }
        });


        /*Button four = (Button) this.findViewById(R.id.button_tuisong);
        four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                webviewt.loadUrl(url);


                    }
        });*/







    }

}


