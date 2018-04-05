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

public class register extends AppCompatActivity {
    private EditText et_registeruser;
    private EditText et_registerpassword;
    private EditText et_registerpasswordagain;
    private EditText et_registerphone;
    private static final int SHOW_RESPONSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_registeruser = (EditText) findViewById(R.id.registeruser);
        et_registerpassword = (EditText) findViewById(R.id.registerpassword);
        et_registerpasswordagain = (EditText) findViewById(R.id.registerpasswordagain);
        et_registerphone = (EditText) findViewById(R.id.registerphone);
        //光标设置在第一行
        et_registeruser.setFocusable(true);
        et_registeruser.requestFocus();
        et_registeruser.setFocusableInTouchMode(true);
//        Button btnregeisterenter = (Button)findViewById(R.id.registerenter);
//        btnregeisterenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(register.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    Toast.makeText(register.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    if (msg.obj.toString().equals("注册成功")) {
//                        AppManager.getAppManager().addActivity(register.this);
//                        AppManager.getAppManager().finishActivity();
                        finish();
                    }
            }
        }
    };

    public void onClick(View v) {
        if (v.getId() == R.id.reset) {
            et_registeruser.setText("");
            et_registerpassword.setText("");
            et_registerpasswordagain.setText("");
            et_registerphone.setText("");
        } else if (v.getId() == R.id.confirm) {
            final String name = et_registeruser.getText().toString().trim();
            final String pass = et_registerpassword.getText().toString().trim();
            final String pass2 = et_registerpasswordagain.getText().toString().trim();
            final String phone = et_registerphone.getText().toString().trim();
            if (name.equals("") || pass.equals("") || pass2.equals("") || phone.equals("")) {
                Toast.makeText(this, "输入不能为空！", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(pass2)) {
                Toast.makeText(this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
            }else if(pass.length()<6) {
                Toast.makeText(this, "密码长度不得少于6位！", Toast.LENGTH_SHORT).show();
            }else if (phone.length() != 11) {
                Toast.makeText(this, "手机号的位数是11位！", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path = "http://47.95.214.133:8080/TsfTicket/RegisTer?name=" + name + "&pass=" + pass +"&phone=" + phone;
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
        }
    }
}
