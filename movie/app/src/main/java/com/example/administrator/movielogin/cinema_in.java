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
import android.widget.TextView;

import com.example.administrator.movielogin.widget.adapters.MyAdapter_Cinema_in;

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

public class cinema_in extends AppCompatActivity {
    private static int SHOW_RESPONSE;
    //    private LinearLayout linear1;
    private JSONObject JsonOut;
    private List<String> cinema_in_Info;
    private ListView listView;
    private String cinemaName;
    private TextView et_cinema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_in);
        et_cinema = (TextView) findViewById(R.id.cinemaintitlecinemaname);
        ImageView btnback = (ImageView) findViewById(R.id.cinemainbtnback);
        btnback.setOnClickListener(mGoBack);
        Intent intent=getIntent();
        cinemaName=intent.getStringExtra("cinemaName");
        et_cinema.setText(cinemaName);
        JsonOut=new JSONObject();
        cinema_in_Info=new ArrayList<String>();
        SHOW_RESPONSE=0;
        this.listView= (ListView) findViewById(R.id.cinemain_info);

        ConnectServe(cinemaName);
    }

    public void JsonExplain(JSONObject T) throws JSONException {
        String keyOut;
        JsonOut = T;
        Iterator iterOut = JsonOut.keys();
        while (iterOut.hasNext()) {
            keyOut = (String) iterOut.next();
            JSONObject JsonInt = (JSONObject) JsonOut.get(keyOut);
            {
                boolean b = false;
                for (String str : cinema_in_Info) {
                    if (str.contains(JsonInt.get("movie").toString())) {
                        b = true;
                    }
                }
                if (!b) cinema_in_Info.add(JsonInt.get("movie").toString());
            }
        }
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject_Init = new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_Init);
                        final MyAdapter_Cinema_in adapter = new MyAdapter_Cinema_in(cinema_in.this, cinema_in_Info);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(cinema_in.this, "Click item" + cinema_in_Info.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://www.baike.com/wiki/"+cinema_in_Info.get(i));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Cinema_in.onItemGetListener() {
                            public void onGetClick(int i) {
                                Intent intent1 = new Intent(cinema_in.this, information.class);
                                intent1.putExtra("movieName", cinema_in_Info.get(i));
                                intent1.putExtra("cinemaName",cinemaName);
                                startActivity(intent1);//自己设置跳转界面
//                                Toast.makeText(cinema.this, "Click item" + cinemaInfo.get(i), Toast.LENGTH_SHORT).show();

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }

        }
    };
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
                    Message msg = new Message();
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
}
