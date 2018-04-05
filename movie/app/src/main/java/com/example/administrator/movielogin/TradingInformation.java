package com.example.administrator.movielogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.movielogin.widget.adapters.MyAdapter_TradingInformation;

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

public class TradingInformation extends AppCompatActivity {
    private String username;
    private static int SHOW_RESPONSE;
    private JSONObject JsonOut;

    private List<String> id;
    private List<String> movie;
    private List<String> cinema;
    private List<String> date;
    private List<String> price;
    private List<String> number;
    private List<String> seat;
    private ListView listView;

    private EditText et_price;
    private EditText et_number;
    private EditText et_seat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_information);
        ImageView btnback = (ImageView) findViewById(R.id.showbtnback);
        btnback.setOnClickListener(mGoBack);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        username = myApplication.getName();
        JsonOut = new JSONObject();
        id = new ArrayList<String>();
        movie = new ArrayList<String>();
        cinema = new ArrayList<String>();
        date = new ArrayList<String>();
        price = new ArrayList<String>();
        number = new ArrayList<String>();
        seat = new ArrayList<String>();
        SHOW_RESPONSE = 0;
        this.listView = (ListView) findViewById(R.id.infomationinfo);
        ConnectServe("SearchByUserUntraded", username);
    }
//    Handler handler1 = new Handler();

    private void refresh() {
        finish();
        Intent intent = new Intent();
        intent.setClass(TradingInformation.this, TradingInformation.class);
        startActivity(intent);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        JSONObject jsonObject_Init = new JSONObject(msg.obj.toString());
                        JsonExplain(jsonObject_Init);
//                        Toast.makeText(information.this,jsonObject_Init.toString(),Toast.LENGTH_SHORT).show();
                        final MyAdapter_TradingInformation adapter = new MyAdapter_TradingInformation(TradingInformation.this, id, movie, date, cinema, price,number, seat);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                Toast.makeText(tradeinfomation.this, "Click item" + id.get(i), Toast.LENGTH_SHORT).show();
                            }
                        });
                        adapter.setOnItemGetClickListener_delete(new MyAdapter_TradingInformation.onItemGetListener() {
                            public void onGetClick(final int i) {
                                //自己设置跳转界面
                                new AlertDialog.Builder(TradingInformation.this)
                                        .setTitle("确认删除吗？")
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ConnectServe("deleteMovie", id.get(i));
//                                                if (msg1.obj.toString().equals("删除电影信息成功!")) {
                                                Toast.makeText(TradingInformation.this, "删除成功", Toast.LENGTH_SHORT).show();
//                                                }
                                                refresh();
                                            }
                                        })
                                        .show();
                            }
                        });

                        adapter.setOnItemGetClickListener_trade(new MyAdapter_TradingInformation.onItemGetListener() {
                            public void onGetClick(final int i) {
                                //自己设置跳转界面
                                new AlertDialog.Builder(TradingInformation.this)
                                        .setTitle("确认交易吗？")
                                        .setNegativeButton("取消", null)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ConnectServe("tradeSuccess", id.get(i));
//                                                if (msg1.obj.toString().equals("删除电影信息成功!")) {
                                                Toast.makeText(TradingInformation.this, "交易成功", Toast.LENGTH_SHORT).show();
//                                                }
                                                refresh();
                                            }
                                        })
                                        .show();
                            }
                        });

                        adapter.setOnItemGetClickListener_update(new MyAdapter_TradingInformation.onItemGetListener() {
                            public void onGetClick(final int i) {
                                //自己设置跳转界面
                                LayoutInflater factory = LayoutInflater.from(TradingInformation.this);
                                final View textEntryView = factory.inflate(R.layout.dialog, null);
                                final EditText et_Price = (EditText) textEntryView.findViewById(R.id.editTextPrice);
                                final EditText et_Num = (EditText)textEntryView.findViewById(R.id.editTextNum);
                                final EditText et_Seat = (EditText)textEntryView.findViewById(R.id.editTextSeat);
                                et_Price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                et_Num.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                                AlertDialog.Builder ad1 = new AlertDialog.Builder(TradingInformation.this);
                                ad1.setTitle("修改影票信息:");
                                ad1.setIcon(android.R.drawable.ic_dialog_info);
                                ad1.setView(textEntryView);
                                ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String Price = et_Price.getText().toString().trim();
                                        String Number = et_Num.getText().toString().trim();
                                        String Seat = et_Seat.getText().toString().trim();
                                        if (Price.equals("") || Number.equals("") || Seat.equals("")) {
                                            Toast.makeText(TradingInformation.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                                        } else {
                                            ConnectServeUpdate(id.get(i), Price, Seat, Number);
                                            Toast.makeText(TradingInformation.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            refresh();
                                        }

                                    }
                                });
                                ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {

                                    }
                                });
                                ad1.show();// 显示对话框
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

            if (username.equals(JsonInt.get("user").toString())) {
                id.add(JsonInt.get("id").toString());
                movie.add(JsonInt.get("movie").toString());
                cinema.add(JsonInt.get("cinema").toString());
                date.add(JsonInt.get("date").toString());
                number.add(JsonInt.get("number").toString());
                price.add(JsonInt.get("price").toString());
                seat.add(JsonInt.get("seat").toString());
            }

        }
    }

    public void ConnectServe(final String action, final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieSeller?user=" + str + "&FlagSeller=SearchByUserUntraded";
                if (action.equals("deleteMovie")) {
                    path = "http://47.95.214.133:8080/TsfTicket/movieSeller?id=" + str + "&FlagSeller=deleteMovie";
                } else if (action.equals("tradeSuccess")) {
                    path = "http://47.95.214.133:8080/TsfTicket/movieSeller?id=" + str + "&FlagSeller=tradeSuccess";
                }
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
//                    msg.what = SHOW_RESPONSE;
                    msg.obj = response.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ConnectServeUpdate(final String id, final String price, final String seat, final String number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieSeller?id=" + id + "&price=" + price
                        + "&seat=" + seat + "&number=" + number + "&FlagSeller=movieUpdate";

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
