package com.example.administrator.movielogin;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText et_loginuser;
    private EditText et_loginpassword;
    private static final int SHOW_RESPONSE = 0;
    private String name;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_loginuser = (EditText) findViewById(R.id.loginuser);
        et_loginpassword = (EditText) findViewById(R.id.loginpassword);
        //光标设置在第一行
        et_loginuser.setFocusable(true);
        et_loginuser.requestFocus();
        et_loginuser.setFocusableInTouchMode(true);
//        Button btnlogin = (Button)findViewById(R.id.login);
//        Button btnregister = (Button)findViewById(R.id.register);
//        btnregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,register.class);
//                startActivity(intent);
//            }
//        });
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,cinema.class);
//                startActivity(intent);
//            }
//        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    if(msg.obj.toString().equals("登录成功")) {
//                        AppManager.getAppManager().addActivity(MainActivity.this);
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,Null.class);
                        MyApplication application = (MyApplication)getApplicationContext();
                        application.setName(name);
//                        intent.putExtra("name",name);
                        MainActivity.this.startActivity(intent);
//                        AppManager.getAppManager().finishActivity();
                        finish();
                    }
            }
        }
    };

    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            name = et_loginuser.getText().toString().trim();
            pass = et_loginpassword.getText().toString().trim();
            if (name.equals("") || pass.equals("")) {
                Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = "http://47.95.214.133:8080/TsfTicket/LoginCheck?name=" + name + "&pass=" + pass;
                        try {
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            conn.setReadTimeout(5000);
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            Message msg = new Message();
                            msg.what = SHOW_RESPONSE;
                            msg.obj = response.toString();
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
//            finish();
        } else if (v.getId() == R.id.register) {
//            Intent intent = new Intent(MainActivity.this,register.class);
//            startActivity(intent);
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, register.class);
            MainActivity.this.startActivity(intent);
//            finish();
        }
    }
}
