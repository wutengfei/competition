package com.example.five_two;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class UnderwayActivity extends AppCompatActivity {

    MediaPlayer player;
    private FivestarPanel fivestarPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bmob.initialize(this, "8f1fd8777e9827d1567a0ae2b00d8746");
        setContentView(R.layout.activity_underway);


        //Button b_score = (Button) findViewById(R.id.button_score);
        //final List<BmobObject> gameScores = new ArrayList<BmobObject>();
        //final MyUser myUser = new MyUser();
        //final GameScore gameScore= new GameScore();
        //final TextView tv1=(TextView)findViewById(R.id.textView4);
        final ToggleButton tB = (ToggleButton) findViewById(R.id.button_son);
        //final BmobQuery<GameScore> bmobQuery	 = new BmobQuery<GameScore>();
        tB.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                if (tB.isChecked()) {
                    tB.setBackgroundResource(R.drawable.aoff);
                    player = MediaPlayer.create(UnderwayActivity.this, R.raw.lucky);
                    player.start();
                    player.setLooping(true);

                } else {
                    player.pause();
                    tB.setBackgroundResource(R.drawable.aon);

                }
            }
        });

        fivestarPanel = (FivestarPanel) findViewById(R.id.id_fivestar);
        ImageButton more2 = (ImageButton) findViewById(R.id.more2);
        more2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                fivestarPanel.start();
            }
        });

        Button Button5 = (Button)this.findViewById(R.id.button_rules);
        Button5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(UnderwayActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });


        /*b_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = (String) MyUser.getObjectByKey("username");
                final AlertDialog.Builder builder = new AlertDialog.Builder(UnderwayActivity.this,R.style.AlertDialog);
                //tv1.setText(username);
                //gameScore.setName(username);
                //gameScore.setPlayscore(score);
                //gameScore.setPlayer(myUser);
                //gameScores.add(gameScore);
                Integer playscore = (Integer) MyUser.getObjectByKey("playscore");

                builder.setTitle("历史成绩");
                builder.setMessage("您的最好成绩是："+playscore);

                builder.setPositiveButton("返回主菜单",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent=new Intent(UnderwayActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("再来一局",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        fivestarPanel.start();
                    }
                });
                builder.create().show();


                /*BmobQuery query =new BmobQuery("GameScore");
                boolean isCache = bmobQuery.hasCachedResult(GameScore.class);
                if(isCache){
                    bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
                }
                query.getObjectByTable(username, new QueryListener<JSONObject>() {
                    @Override
                    public void done(JSONObject jsonObject, BmobException e) {
                        if(e==null){
                            tv1.setText(gameScore.getPlayscore());
                        }else{
                            tv1.setText(username);
                        }
                    }
                });




            }
        })*/


    }


}
