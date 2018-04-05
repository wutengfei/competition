package com.example.administrator.movielogin;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.movielogin.widget.ChatMsgEntity;
import com.example.administrator.movielogin.widget.adapters.ChatMsgViewAdapter;

import Control.ChatRecordControl;
import Model.ChatRecord;
import Urls.SocketUrls;

public class chat extends Activity implements Runnable {
    private String contString, username, Sellername, message;
    private EditText mEditTextContent;
    private ListView mListView1;
    private ChatMsgViewAdapter mAdapter1;
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
    private TextView textView;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private ChatRecord chatRecord;
    private ChatRecordControl chatRecordControl;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mListView1 = (ListView) findViewById(R.id.listview1);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
        textView = (TextView) findViewById(R.id.tv_name);

        try {
            socket = new Socket(SocketUrls.IP, SocketUrls.PORT);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            ShowDialog("login exception:" + ex.getMessage());
        }
        new Thread(chat.this).start();
        mAdapter1 = new ChatMsgViewAdapter(this, mDataArrays);
        mListView1.setAdapter(mAdapter1);//客户一页面

        chatRecordControl=new ChatRecordControl(this);
    }

    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                // TODO Auto-generated method stub
                MyApplication myApplication = (MyApplication) getApplicationContext();
                username = myApplication.getName();

                String msg = mEditTextContent.getText().toString();
                msg=msg+" "+username;
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        out.println(msg);
                    }
                }
                send_message();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    private void send_message () {
        contString = mEditTextContent.getText().toString();
        if (contString.length() == 0) {
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void run () {
        try {
            while (true) {
                if (socket.isConnected()) {
                    if (!socket.isInputShutdown()) {
                        if ((content = in.readLine()) != null) {
                            Character(content);
//							content += "\n";
                            mHandler.sendMessage(mHandler.obtainMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            initData(content);
        }
    };

    public void initData (String content) {
        textView.setText(Sellername);
        if (!content.equals("")) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("");
            entity.setDate(getDate());
            entity.setMessage(message);
            entity.setMsgType(true);
            mDataArrays.add(entity);
            mAdapter1.notifyDataSetChanged();
            mEditTextContent.setText("");
            mListView1.setSelection(mListView1.getCount() - 1);


            //将聊天记录添加到数据库中
//            chatRecord=new ChatRecord(username,message);
//            chatRecordControl.addChatRecord(chatRecord);
        }
    }

    private String getDate () {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

    private void ShowDialog (String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    public void onClick (DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).show();
    }

    private void Character (String content) {
        String[] strings = new String[2];
        int i = 0;
        for (String temp : content.split(" ")) {
            strings[i] = temp;
            i++;
        }
        message = strings[0];
        Sellername = strings[1];
    }
}
