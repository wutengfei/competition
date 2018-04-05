package com.example.administrator.movielogin;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class user extends Fragment {
    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private EditText edit;
    private TextView tv_name;
    private String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user ,null);
        tv_name = (TextView) rootView.findViewById(R.id.textView11);
        linear1 = (LinearLayout)rootView.findViewById(R.id.lineartrading);
        linear7 = (LinearLayout)rootView.findViewById(R.id.lineartraded);
        linear2 = (LinearLayout)rootView.findViewById(R.id.linearbuy);
        linear3 = (LinearLayout)rootView.findViewById(R.id.linearmessage);
        linear4 = (LinearLayout)rootView.findViewById(R.id.linearchangepassword);
        linear5 = (LinearLayout)rootView.findViewById(R.id.linearaboutus);
        linear6 = (LinearLayout)rootView.findViewById(R.id.linearexit);

        linear1.setOnClickListener(onclicklistener);
        linear2.setOnClickListener(onclicklistener);
        linear3.setOnClickListener(onclicklistener);
        linear4.setOnClickListener(onclicklistener);
        linear5.setOnClickListener(onclicklistener);
        linear6.setOnClickListener(onclicklistener);
        linear7.setOnClickListener(onclicklistener);

//        myApplication = (MyApplication)getApplicationContext();
//        Intent intent = getIntent();
//        username = intent.getStringExtra("name");
//        tv_name.setText("用户名："+username);
        MyApplication myApplication = (MyApplication)getActivity().getApplicationContext();
        username = myApplication.getName();
        tv_name.setText("用户名："+username);
        return rootView;
    }


    public View.OnClickListener onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lineartrading:
                    Intent intent1= new Intent();
                    intent1.setClass(getActivity(),TradingInformation.class);
                    startActivity(intent1);
                    break;
                case R.id.lineartraded:
                    Intent intent7= new Intent();
                    intent7.setClass(getActivity(),tradeinfomation.class);
                    startActivity(intent7);
                    break;
                case R.id.linearbuy:
                    Intent intent2= new Intent();
                    intent2.setClass(getActivity(),addmovie.class);
                    startActivity(intent2);
                    break;
                case R.id.linearmessage:
                    Intent intent3= new Intent();
                    intent3.setClass(getActivity(),contect.class);
                    startActivity(intent3);
                    break;
                case R.id.linearchangepassword:
                    edit = new EditText(getActivity());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("修改密码");
                    edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    builder.setView(edit);
                    builder.setMessage("确定要修改密码么？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String pass = edit.getText().toString().trim();
                            if(pass.equals("")) {
                                Toast.makeText(getActivity(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                            }else if(pass.length()<6) {
                                Toast.makeText(getActivity(), "密码长度不得少于6位！", Toast.LENGTH_SHORT).show();
                            }else{
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String path = "http://47.95.214.133:8080/TsfTicket/UpdatePassword?name=" + username + "&pass=" + pass;
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
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),"取消修改",Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog b = builder.create();
                    b.show();
                    break;
                case R.id.linearaboutus:
                    Intent intent5= new Intent();
                    intent5.setClass(getActivity(),aboutus.class);
                    startActivity(intent5);
                    break;
                case R.id.linearexit:
                    Intent intent6= new Intent();
                    intent6.setClass(getActivity(),MainActivity.class);
                    startActivity(intent6);
                    getActivity().finish();
                    break;
            }
        }
    };
}
