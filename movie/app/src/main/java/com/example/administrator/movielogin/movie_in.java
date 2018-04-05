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

import com.example.administrator.movielogin.widget.adapters.MyAdapter_Movie_in;

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

public class movie_in extends AppCompatActivity {
    private static int SHOW_RESPONSE;
    //    private LinearLayout linear1;
    private TextView et_moviename;
    private JSONObject JsonOut;
    private List<String> movie_in_Info;
    private ListView listView;
    private String movieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_in);
        ImageView btnback = (ImageView) findViewById(R.id.movieinbtnback);
        btnback.setOnClickListener(mGoBack);
        et_moviename = (TextView) findViewById(R.id.movieintitlemoviename);
        Intent intent=getIntent();
        movieName=intent.getStringExtra("movieName");
        et_moviename.setText(movieName);
        JsonOut=new JSONObject();
        movie_in_Info=new ArrayList<String>();
        SHOW_RESPONSE=0;
        this.listView= (ListView) findViewById(R.id.movieinfomation);

        ConnectServe(movieName);
    }

    public View.OnClickListener mGoBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public void JsonExplain(JSONObject T) throws JSONException {
        String keyOut;
        JsonOut = T;
        Iterator iterOut = JsonOut.keys();
        while (iterOut.hasNext()) {
            keyOut = (String) iterOut.next();
            JSONObject JsonInt = (JSONObject) JsonOut.get(keyOut);
            {
                boolean b = false;
                for (String str : movie_in_Info) {
                    if (str.contains(JsonInt.get("cinema").toString())) {
                        b = true;
                    }
                }
                if (!b) movie_in_Info.add(JsonInt.get("cinema").toString());
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
                        final MyAdapter_Movie_in adapter = new MyAdapter_Movie_in(movie_in.this, movie_in_Info);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(movie_in.this, "Click item" + movie_in_Info.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://piao.163.com/beijing/cinema/category-ALL-area-0-type-0.html?keywords="+movie_in_Info.get(i)+"#from=cinema.search");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);

                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Movie_in.onItemGetListener() {
                            public void onGetClick(int i) {
                                Intent intent1 = new Intent(movie_in.this, information.class);
//                                    intent1.putExtra("cinemaName", cinema_in_Info.get(i));
                                intent1.putExtra("movieName", movieName);
                                intent1.putExtra("cinemaName",movie_in_Info.get(i));
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



    public void ConnectServe(final String movieAbout_editTextIn) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieBuyer?name="+movieAbout_editTextIn+"&FlagBuyer=SearchByMovie&flagMovieInfo=name";
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
}
