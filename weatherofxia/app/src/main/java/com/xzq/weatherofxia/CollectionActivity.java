package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.xzq.weatherofxia.bean.myCollection;
import com.xzq.weatherofxia.db.MyCollection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lenovo on 2017/11/26.
 */

/**
 * 用于展示已收藏城市的适配器
 */
public class CollectionActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView lv_collection;
    private ArrayList<myCollection> collections;
    private CollectonAdapter myAdapter;
    private Context mContext;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        lv_collection= (ListView) findViewById(R.id.lv_collection);
        mContext = CollectionActivity.this;
        username =getIntent().getStringExtra("username");
        collections=new ArrayList<myCollection>();//实例化
        getCollection();
        lv_collection.setOnItemClickListener(this);
    }

    /**
     * 查询该用户收藏过的城市，并在ListView中显示
     */
    public void getCollection(){
        collections.clear();
        MyCollection myCollection =new MyCollection(this);
        collections =myCollection.queryCityname(username);
        myAdapter=new CollectonAdapter(this,collections);
        lv_collection.setAdapter(myAdapter);
    }

    /**
     * 监听ListView的点击事件，用于跳转到查询天气的Activity
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String city = collections.get(position).cityname;
        CollectionActivity.this.finish();
        Intent intent = new Intent(CollectionActivity.this,fMainActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode,KeyEvent event){//返回重新启动MainActivity
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            CollectionActivity.this.finish();
            Intent intent = new Intent(CollectionActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
