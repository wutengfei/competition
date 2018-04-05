package com.xzq.weatherofxia;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xzq.weatherofxia.db.MyCollection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class fMainActivity extends Activity {
    private ListView lv_weather;
    private ArrayList<StringBuilder> sbs;
    private WeatherAdapter myAdapter;
    private MyCollection myCollection;
    private String username;
    private Button bt_collect;
    HashMap<String,String>keysMeans=new HashMap<String, String>();//创建一个集合
    private AlertDialog dialog;
    private String city;
   // private String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_weather= (ListView) findViewById(R.id.lv_weather);
        myCollection = new MyCollection(this);
        setKeys();//调用setKeys(),作用是翻译从API中得到的信息
        username = getIntent().getStringExtra("username");
        city = getIntent().getStringExtra("city");//得到intent传来的city键的信息
        getWeather();//获取天气信息
        bt_collect = (Button) findViewById(R.id.bt_collect);
        bt_collect.setOnClickListener(new BtnClickListener());
    }

    /**
     * 收藏按钮的监听，处理收藏和取消收藏城市天气
     */
        class BtnClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                String cityname = city;
               // Toast.makeText(fMainActivity.this, cityname + "夏子青" + username, Toast.LENGTH_SHORT).show();
                if (Check(username, cityname)) {//检查是否收藏过
                    SQLiteDatabase db= myCollection.getWritableDatabase();
                    db.delete("collection","username =? and cityname =?",new String[] {username,cityname});
                    db.close();
                    Toast.makeText(fMainActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    bt_collect.setActivated(false);
                } else {
                    if (Sign(username, cityname)) {//插入数据
                        Toast.makeText(fMainActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        bt_collect.setActivated(true);
                    }
                }
            }
        }

    /**
     * 开一个线程，用于请求网络，从API获取请求信息
     */
        private void getWeather(){
            configDialog(true);//显示对话框
            new Thread(){
                public void run(){
                    try{
                        String cityName= URLEncoder.encode(city,"utf-8");//将得到的city转成utf-8格式
                        URL url=new URL("https://www.sojson.com/open/api/weather/json.shtml?city="+cityName);//请求网址
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//打开连接
                        InputStream is = connection.getInputStream();//得到网页数据，二进制
                        int x=0;
                        byte[] bys=new byte[1024];//二进制
                        String weatherJson="";//空字符串，接收最终json数据
                        while ((x=is.read(bys))!=-1) {//一直读到最后
                            weatherJson += new String(bys, 0, x);//bys是1kb大小，x可以用来判定数据多大。
                        }
                        configDialog(false);
                        parseWeather(weatherJson);//查询天气
                        //System.out.println(weatherJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                        configDialog(false);//消隐对话框
                    }
                }
            }.start();
        }

    /**
     * 在HashMap集合中设置键值对，用于翻译从API返回的信息
     *
     */
        private void setKeys(){
            keysMeans.put("yesterday","昨天");//键值对，用于翻译
            keysMeans.put("date","日期");
            keysMeans.put("high","高温");
            keysMeans.put("fx","南风");
            keysMeans.put("low","低温");
            keysMeans.put("fl","微风");
            keysMeans.put("type","类型");
            keysMeans.put("city","城市");
            keysMeans.put("aqi","空气质量指数");
            keysMeans.put("forecast","未来");
            keysMeans.put("fengli","风力");
            keysMeans.put("fengxiang","风向");
            keysMeans.put("ganma","注意");
            keysMeans.put("wendu","温度");
            keysMeans.put("sunrise","日出时间");
            keysMeans.put("sunset","日落时间");
            keysMeans.put("notice","注意");
            //HashSet<String> set=new HashSet<String>();
            //HashSet<String> array=new HashSet<String>();

        }

    /**
     * 对就收到的Json数据进行解析，并且在listview中显示
     * @param weatherJson   接收到的Json数据
     * @throws Exception
     */
        protected void parseWeather(String weatherJson)throws Exception {
            JSONObject object = new JSONObject(weatherJson);//根据服务器返回的string数据 创建一个jsonobject对象
            //获取object对象中status的值
            String status = object.getString("status");
            sbs=new ArrayList<StringBuilder>();
            if ("200".equals(status)) {
                //判断status的值是否为200 如果是，说明天气信息查询成功
                JSONObject intoobject = object.getJSONObject("data");//根据API返回信息的格式取出想要的
                JSONArray forecast = intoobject.getJSONArray("forecast");
                for(int i=0;i<forecast.length();i++){
                JSONObject jsonToday=forecast.getJSONObject(i);//JSONArray里第一项
                Iterator<String> keys =jsonToday.keys();//取出retData的迭代器，实际存放着键，类似指针
                final StringBuilder sBuilder=new StringBuilder();//字符串容器，线程不安全，但是StringBuffer拖慢效率
                while(keys.hasNext()){//迭代器的hasNext方法，遍历所有的键
                    String next =keys.next();//取出键
                    sBuilder.append(keysMeans.get(next)+" : "+jsonToday.getString(next)+"\n");//将数据存储到字符串容器中
                }
                    sbs.add(sBuilder);
                }
                runOnUiThread(new Runnable() {//在主线程中运行，因为给控件设置属性要在主线程中运行，runOnThread顾名思义
                    @Override
                    public void run() {
                        myAdapter=new WeatherAdapter(fMainActivity.this,sbs);
                       lv_weather.setAdapter(myAdapter);//将字符串容器中的值放入text中
                    }
                });
            }else {
                configDialog(false);//消隐
            }
        }

    /**
     * 创建一个对话框，用于提醒用户网络连接情况
     * @param b
     */
    private void configDialog(boolean b) {
        if(b){
            if(dialog==null){//检测全局变量对话框是否为空，为空则创建一个
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("网络连接中");//设置属性
                builder.setMessage("正在获取数据，请稍候。。。");
                builder.setCancelable(false);//用户不能点击取消对话框
                dialog = builder.create();
            }
            dialog.show();//显示
        }else{
            dialog.dismiss();
        }
    }

    /**
     * 在数据库中查找该用户是否收藏过该城市
     * @param username 用户名
     * @param cityname 城市名
     * @return true or false
     */
    public boolean Check(String username,String cityname){//检测是否收藏过
        SQLiteDatabase db=myCollection.getWritableDatabase();
        String Query = "Select * from collection where username =? and cityname =?";//SQLITE查询语言
        Cursor cursor = db.rawQuery(Query,new String[] {username,cityname});//查询名字
        if (cursor.getCount()>0){//收藏过
//            db.delete("collection","username =? and cityname =?",new String[] {username,cityname});
            cursor.close();
            return  true;
        }
        cursor.close();
        db.close();
        return false;//没注册过
    }

    /**
     * 像收藏信息的数据库中插入用户名和城市名
     * @param username 用户名
     * @param cityname 城市名
     * @return true
     */
    public boolean Sign(String username,String cityname){//向数据库插入数据
        SQLiteDatabase db= myCollection.getWritableDatabase();
        ContentValues values=new ContentValues();//插入用户名和城市
        values.put("username",username);
        values.put("cityname",cityname);
        db.insert("collection",null,values);//按数据库名字插入
        db.close();
        return true;
    }

    /**
     * 重写返回键，用于重新启动MainActivity
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode,KeyEvent event){//返回重新启动MainActivity
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            fMainActivity.this.finish();
            Intent intent = new Intent(fMainActivity.this,MainActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
