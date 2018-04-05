package com.example.administrator.movielogin;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.movielogin.widget.adapters.MyAdapter_information;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class information extends AppCompatActivity {
    private static int SHOW_RESPONSE;
    private JSONObject JsonOut;

    private List<String> Initial;
    private List<String> id;
    private List<String> movie;
    private List<String> cinema;
    private List<String> date;
    private List<String> user;
    private List<String> phone;
    private List<String> price;
    private List<String> number;
    private List<String> seat;
    private ListView listView;
    private String cinemaName;
    private String movieName;
private Boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ImageView btnback = (ImageView) findViewById(R.id.showbtnback);
        btnback.setOnClickListener(mGoBack);
        Intent intent = getIntent();

        movieName = intent.getStringExtra("movieName");
        cinemaName = intent.getStringExtra("cinemaName");
//        Toast.makeText(information.this,movieName+"----"+cinemaName,Toast.LENGTH_SHORT).show();
        JsonOut = new JSONObject();
        Initial = new ArrayList<String>();
        id = new ArrayList<String>();
        movie = new ArrayList<String>();
        cinema = new ArrayList<String>();
        date = new ArrayList<String>();
        user = new ArrayList<String>();
        phone = new ArrayList<String>();
        price = new ArrayList<String>();
        number = new ArrayList<String>();
        seat = new ArrayList<String>();
        SHOW_RESPONSE = 0;
        this.listView = (ListView) findViewById(R.id.infomationinfo);

        ConnectServe(cinemaName);
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject_Init = new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_Init);
//                        Toast.makeText(information.this,jsonObject_Init.toString(),Toast.LENGTH_SHORT).show();
                        final MyAdapter_information adapter = new MyAdapter_information(information.this, id, movie, date, cinema, user, phone, price, number, seat);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(cinema_in.this, "Click item" + cinema_in_Info.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });
                        adapter.setOnItemGetClickListener_chat(new MyAdapter_information.onItemGetListener() {
                            public void onGetClick(int i) {
                                //自己设置跳转界面
                                Intent intent = new Intent(information.this, chat.class);
//                                Intent intent1=new Intent(information.this,contect.class);
//                                intent.putExtra("username",user.get(i));
                                MyApplication myapplication = (MyApplication) getApplicationContext();
                                for(int j=0;j<myapplication.getSize();j++){
                                    if(myapplication.getUser_name(i).equals(user.get(i))){
                                        flag=false;
                                        break;
                                    }
                                }
                                if(flag){
                                    myapplication.setUser_name(user.get(i));
                                }
                                flag=true;
//                                myapplication.setUser_name(user.get(i));
                                startActivity(intent);
//                                Toast.makeText(cinema.this, "Click item" + cinemaInfo.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });

                        adapter.setOnItemGetClickListener_phone(new MyAdapter_information.onItemGetListener() {
                            public void onGetClick(int i) {
                                //自己设置跳转界面
                                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.get(i)));//跳转到拨号界面，同时传递电话号码
                                startActivity(dialIntent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };


    public void JsonExplain(JSONObject T) throws JSONException {
        String keyOut;
        JsonOut = T;
        Iterator iterOut = JsonOut.keys();
        while (iterOut.hasNext()) {
            keyOut = (String) iterOut.next();
            JSONObject JsonInt = (JSONObject) JsonOut.get(keyOut);
            if (movieName.equals(JsonInt.get("movie").toString())) {
                id.add(JsonInt.get("id").toString());
                movie.add(JsonInt.get("movie").toString());
                cinema.add(JsonInt.get("cinema").toString());
                date.add(JsonInt.get("date").toString());
                user.add(JsonInt.get("user").toString());
                phone.add(JsonInt.get("phone").toString());
                price.add(JsonInt.get("price").toString());
                number.add(JsonInt.get("number").toString());
                seat.add(JsonInt.get("seat").toString());
            }
        }
    }
    Message msg = new Message();

    public void ConnectServe(final String cinemaAbout_editTextIn) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieBuyer?cinema=" + cinemaAbout_editTextIn + "&FlagBuyer=SearchByCinema";
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
                    msg.what = SHOW_RESPONSE;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public View.OnClickListener mGoBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
//    public void onClick(View v){
//        if (v.getId() == R.id.chat){
//            Intent intent = new Intent();
//            intent.setClass(information.this,chat.class);
//            information.this.startActivity(intent);
//        }
//    }
}
