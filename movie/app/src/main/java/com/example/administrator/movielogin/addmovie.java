package com.example.administrator.movielogin;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class addmovie extends AppCompatActivity implements View.OnClickListener {

    private Button mStartDateButton;
    private TextView mShowContentTextView;
//    private Button mEnterButton;
    private static final int SHOW_RESPONSE = 0;
    private String username;
    private EditText et_movie;
    private EditText et_cinema;
    private TextView tv_date;
    private EditText et_price;
    private EditText et_number;
    private EditText et_seat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);
//        AppManager.getAppManager().addActivity(addmovie.this);
        et_movie = (EditText) findViewById(R.id.et_moviename);
        et_cinema = (EditText) findViewById(R.id.et_cinema);
        et_price = (EditText) findViewById(R.id.et_price);
        et_number = (EditText) findViewById(R.id.et_number);
        et_seat = (EditText) findViewById(R.id.et_seat);


        mStartDateButton = (Button) this.findViewById(R.id.start_date_btn);
        mShowContentTextView = (TextView) this.findViewById(R.id.show_content_tv);
        mStartDateButton.setOnClickListener(this);
//        mEnterButton=(Button) this.findViewById(R.id.button_enter);
//        mEnterButton.setOnClickListener(mEnter);

        MyApplication myApplication = (MyApplication)getApplicationContext();
        username = myApplication.getName();

        tv_date = (TextView) findViewById(R.id.show_content_tv);
    }

    public View.OnClickListener mEnter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            AppManager.getAppManager().finishActivity();
            finish();
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    Toast.makeText(addmovie.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    if (msg.obj.toString().equals("插入成功")) {
//                        AppManager.getAppManager().addActivity(register.this);
//                        AppManager.getAppManager().finishActivity();
                        finish();
                    }
            }
        }
    };

    public void ConnectServe(final String movie, final String cinema, final String date, final String user,
                             final String price, final String number, final String trade, final String seat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "http://47.95.214.133:8080/TsfTicket/movieSeller?FlagSeller=increaseMovie"
                        +"&movie="+movie+"&cinema="+cinema+"&date="+date+"&user="+user+"&price="+price
                        +"&number="+number+"&trade="+trade+"&seat="+seat;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date_btn://开始时间
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(addmovie.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        mShowContentTextView.setText(time);
                    }
                });
                startDateChooseDialog.setDateDialogTitle("开始时间");
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.button_enter:
                String movie = et_movie.getText().toString().trim();
                String cinema = et_cinema.getText().toString().trim();
                String date = tv_date.getText().toString().trim();
                String price = et_price.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                String seat = et_seat.getText().toString().trim();
                ConnectServe(movie, cinema, date, username, price, number, "0", seat);

                break;
            default:
                break;
        }

    }
}
