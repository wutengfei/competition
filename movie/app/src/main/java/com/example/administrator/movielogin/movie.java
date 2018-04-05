package com.example.administrator.movielogin;


import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.movielogin.widget.adapters.MyAdapter_Movie;

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

public class movie extends Fragment {

    private static int SHOW_RESPONSE;
    //    private LinearLayout linear1;
    private JSONObject JsonOut;
    private List<String> MovieInfo;
    private ListView listView;
    private EditText editText;
    private Button btnadd;
    private Button btnmoviesearch;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_movie ,null);
        editText=(EditText)rootView.findViewById(R.id.searchmovie);
        listView= (ListView)rootView.findViewById(R.id.movieinfomation);
        btnmoviesearch= (Button) rootView.findViewById(R.id.moviesearch);
        btnadd= (Button) rootView.findViewById(R.id.btn_add);
        JsonOut=new JSONObject();
        MovieInfo=new ArrayList<String>();
        SHOW_RESPONSE=0;

        ConnectServe("");
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        btnmoviesearch.setOnClickListener(mGoBack);
        btnadd.setOnClickListener(mbtnadd);
//        Toast.makeText(getActivity(),"搜索成功",Toast.LENGTH_SHORT).show();
    }

    public View.OnClickListener mbtnadd =new View.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent(getActivity(),addmovie.class);
            startActivity(intent);
        }
    };


    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
//                    System.out.println((String)msg.obj);
//                    Toast.makeText(cinema.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject_Init=new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_Init);
                        final MyAdapter_Movie adapter = new MyAdapter_Movie(getActivity(), MovieInfo);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(movie.this, "Click item" + MovieInfo.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://www.baike.com/wiki/"+MovieInfo.get(i));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Movie.onItemGetListener(){
                            public void onGetClick(int i){
                                Intent intent1 = new Intent(getActivity(),movie_in.class);
                                intent1.putExtra("movieName",MovieInfo.get(i));
                                startActivity(intent1);//自己设置跳转界面
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        JSONObject jsonObject_EditText=new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_EditText);
                        final MyAdapter_Movie adapter = new MyAdapter_Movie(getActivity(), MovieInfo);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(movie.this, "Click item" + MovieInfo.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://www.baike.com/wiki/"+MovieInfo.get(i));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Movie.onItemGetListener(){
                            public void onGetClick(int i){
                                Intent intent1 = new Intent(getActivity(),movie_in.class);
                                intent1.putExtra("movieName",MovieInfo.get(i));
                                startActivity(intent1);//自己设置跳转界面
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }

        }
    };


    public void JsonExplain(JSONObject T) throws JSONException {


//我要查询的字符串

        String keyOut,keyInt;
        JsonOut =T;
        Iterator iterOut= JsonOut.keys();
        while (iterOut.hasNext())
        {
            keyOut = (String) iterOut.next();
            JSONObject JsonInt= (JSONObject) JsonOut.get(keyOut);
            {
                boolean b =false;
                for(String str:MovieInfo){
                    if(str.contains(JsonInt.get("movie").toString())){
                        b=true;
                    }
                }
                if(!b)  MovieInfo.add(JsonInt.get("movie").toString());
//                Iterator iterInt= JsonInt.keys();
//                while(iterInt.hasNext())
//                {
//                    keyInt =(String) iterInt.next();
//                    System.out.println(keyInt+"="+JsonInt.get(keyInt));
//                    System.out.println("长度="+JsonOut.length());
//                }
            }
        }


    }


    class MyAdapter_Initial extends BaseAdapter {

        //系统调用，用来获知集合中有多少条元素
        @Override
        public int getCount() {
            return MovieInfo.size();
        }

        //由系统调用，获取一个View对象，作为ListView的条目
        //position:本次getView方法调用所返回的View对象，在listView中是处于第几个条目，那么position的值就是多少
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String p=MovieInfo.get(position);
            View v=View.inflate(getActivity(),R.layout.movie_listview,null);
            TextView tvname= (TextView) v.findViewById(R.id.moviename);
            tvname.setText(p);
            return v;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }


    public void ConnectServe(final String movieAbout_editTextIn){
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
                    InputStream is =conn.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                    StringBuilder response =new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null)
                    {
                        response.append(line);
                    }
                    Message msg = new Message();
                    msg.what =SHOW_RESPONSE;
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
            MovieInfo.clear();
            SHOW_RESPONSE=1;
            ConnectServe(editText.getText().toString());

//            Toast.makeText(movie.this,"搜索成功",Toast.LENGTH_SHORT).show();
        }
    };
}
