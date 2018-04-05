package com.xzq.weatherofxia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xzq.weatherofxia.bean.City;
import com.xzq.weatherofxia.db.Mydb;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView list_weather;
    private ArrayList<City> citys;//存放查询城市
    private Context mContext; //上下文
    private cityAdapter myAdapter = null;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        mContext = MainActivity.this;
        list_weather= (ListView) findViewById(R.id.list_weather);
        citys=new ArrayList<City>();//实例化
        username = getIntent().getStringExtra("username");
        System.out.println("下载"+username);
        new Mydb(this).getWritableDatabase();//写数据库到手机
        start();
        list_weather.setOnItemClickListener(this);
    }

    /**
     * 点击直接查询按键 进入DirectActivity界面
     * @param view
     */
    public void query(View view){
        MainActivity.this.finish();
        Intent intent =new Intent(MainActivity.this,DirectActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    /**
     * 用于查询省，并且设置adapter，显示ListView
     */
    public void start(){
        citys.clear();//清空citys集合
        Mydb helper = new Mydb(this);//new个对象，为了用Mydp中的查询函数
        citys = helper.queryCity(0);//查询 省的parent_id为0
        myAdapter = new cityAdapter(citys, mContext);//将citys传入
        list_weather.setAdapter(myAdapter);//设置Adapter
        myAdapter.notifyDataSetChanged();//更新
        //Toast.makeText(mContext, myAdapter.getCount()+"",Toast.LENGTH_SHORT).show();
    }

    /**
     * 我的收藏按钮，点击进入我的收藏界面
     * @param view
     */
    public void mycollection(View view){
        MainActivity.this.finish();
        Intent intent = new Intent(MainActivity.this,CollectionActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    /**
     * 处理列表上的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //如果被点击的条目的parent_id为0（是省）
        if (citys.get(position).parent_id==0) {
            //查询省下辖市区
            int parent_id=citys.get(position).id;//得到对应位置的id，赋给parent_id，用于查询省中的市
            Mydb helper = new Mydb(this);
            ArrayList<City> queryCitys = helper.queryCity(parent_id);
            //如果查询到的下辖市区数量大于0,则不是生
            if (queryCitys.size()>0) {
                citys=queryCitys; //将市赋给citys
                myAdapter = new cityAdapter(citys, mContext);
                list_weather.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }else{
                //是直辖市，直接跳转到查询天气的activity
                startfMainActivity(citys.get(position).name);
            }
            helper.close();
        }else{
            //被点击的条目不是省而是市，跳转到查询天气的activity
            startfMainActivity(citys.get(position).name);
        }
    }

    /**
     * 用于启动fMainActivity
     * @param cityName 所查询城市的名字
     */
    private void startfMainActivity(String cityName) {
        MainActivity.this.finish();
        Intent intent=new Intent(this,com.xzq.weatherofxia.fMainActivity.class);
        //Toast.makeText(MainActivity.this,cityName+"赔"+username,Toast.LENGTH_SHORT).show();
        intent.putExtra("city", cityName);//通过Intent传递数据，键值对
        intent.putExtra("username",username);
//        intent.putExtra("flag","0");
        startActivity(intent);
    }

    /**
     * 重写返回键，使程序出现确认是否退出的提示
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            // 监控返回键
            new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setMessage("确定要退出吗?")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }})
                    .setNegativeButton("取消", null)
                    .create().show();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
