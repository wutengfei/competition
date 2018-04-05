package com.example.administrator.movielogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administrator.movielogin.widget.SwipeListView;
import com.example.administrator.movielogin.widget.WXMessage;
import com.example.administrator.movielogin.widget.adapters.ChatMsgViewAdapter;
import com.example.administrator.movielogin.widget.adapters.SwipeAdapter;


public class contect extends Activity {
    private List<WXMessage> data = new ArrayList<WXMessage>();
    private SwipeListView mListView;
    private String username;
    private SwipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contect);

        initData();
        initView();
    }

    private void initData() {
        MyApplication myapplication = (MyApplication) getApplicationContext();
        WXMessage msg = null;
        for(int i=0;i<myapplication.getSize();i++){
            username = myapplication.getUser_name(i);
            msg = new WXMessage(username);
            msg.setIcon_id(R.drawable.mini_avatar_shadow);
            data.add(msg);
        }
    }

    /**
     * 鍒濆鍖栫晫闈?
     */
    private void initView() {
        mListView = (SwipeListView) findViewById(R.id.listview);
        final SwipeAdapter mAdapter = new SwipeAdapter(this,data,mListView.getRightViewWidth());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnRightItemClickListener(new SwipeAdapter.onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, int position) {

                Toast.makeText(contect.this, "delete " + (position + 1) + " record",
                        Toast.LENGTH_SHORT).show();
                data.remove(position);
                MyApplication myapplication = (MyApplication) getApplicationContext();
                myapplication.remove(position);
                mAdapter.notifyDataSetChanged();
                mListView.hiddenRight(mListView.getmCurrentItemView());
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(contect.this, "item onclick " + position, Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(contect.this, chat.class);
                startActivity(intent);
            }
        });
    }
}

