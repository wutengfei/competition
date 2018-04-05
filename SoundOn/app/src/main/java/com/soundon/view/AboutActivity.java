package com.soundon.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.soundon.R;

public class AboutActivity extends AppCompatActivity {
    private String[]data ={"当前版本","检查更新","作者微博","版权"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(AboutActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView =(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);


    }
}