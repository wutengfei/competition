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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.movielogin.widget.adapters.MyAdapter_Cinema;

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


public class cinema extends Fragment {
    private static int SHOW_RESPONSE;
    //    private LinearLayout linear1;
    private JSONObject JsonOut;
    private List<String> cinemaInfo;
    private ListView listView;
    private EditText editText;
    private Button btn_search;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cinima ,null);
        editText=(EditText)rootView.findViewById(R.id.searchcinema);
        JsonOut=new JSONObject();
        cinemaInfo=new ArrayList<String>();
        SHOW_RESPONSE=0;
        this.listView= (ListView)rootView.findViewById(R.id.cinemainfo);
        btn_search=(Button)rootView.findViewById(R.id.cinemasearch);
//        image.setOnClickListener(mGoBack);
        ConnectServe("");
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        btn_search.setOnClickListener(mGoBack);
//        Toast.makeText(getActivity(),"搜索成功",Toast.LENGTH_SHORT).show();
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    try {
                        JSONObject jsonObject_Init=new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_Init);
                        final MyAdapter_Cinema adapter = new MyAdapter_Cinema(getActivity(), cinemaInfo);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(cinema.this, "Click item" + cinemaInfo.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://piao.163.com/beijing/cinema/category-ALL-area-0-type-0.html?keywords="+cinemaInfo.get(i)+"#from=cinema.search");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Cinema.onItemGetListener(){
                            public void onGetClick(int i){
                                Intent intent1 = new Intent(getActivity(),cinema_in.class);
                                intent1.putExtra("cinemaName",cinemaInfo.get(i));
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
                        final MyAdapter_Cinema adapter = new MyAdapter_Cinema(getActivity(), cinemaInfo);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(getActivity(), "Click item" + cinemaInfo.get(i), Toast.LENGTH_SHORT).show();
                                Uri uri=Uri.parse("http://piao.163.com/beijing/cinema/category-ALL-area-0-type-0.html?keywords="+cinemaInfo.get(i)+"#from=cinema.search");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        adapter.setOnItemGetClickListener(new MyAdapter_Cinema.onItemGetListener(){
                            public void onGetClick(int i){
                                Intent intent1 = new Intent(getActivity(),cinema_in.class);
                                intent1.putExtra("cinemaName",cinemaInfo.get(i));
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
        String keyOut,keyInt;
        JsonOut =T;
        Iterator iterOut= JsonOut.keys();
        while (iterOut.hasNext())
        {
            keyOut = (String) iterOut.next();
            JSONObject JsonInt= (JSONObject) JsonOut.get(keyOut);
            {
                boolean b =false;
                for(String str:cinemaInfo){
                    if(str.contains(JsonInt.get("cinema").toString())){
                        b=true;
                    }
                }
                if(!b)cinemaInfo.add(JsonInt.get("cinema").toString());
            }
        }


    }
    public void ConnectServe(final String cinemaAbout_editTextIn){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieBuyer?cinema="+cinemaAbout_editTextIn+"&FlagBuyer=SearchByCinema";
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
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public View.OnClickListener mGoBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cinemaInfo.clear();
            SHOW_RESPONSE=1;
//            Toast.makeText(getActivity(),"搜索成功",Toast.LENGTH_SHORT).show();
            ConnectServe(editText.getText().toString());
        }
    };
}