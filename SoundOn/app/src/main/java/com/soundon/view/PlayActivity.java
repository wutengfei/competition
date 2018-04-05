package com.soundon.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundon.MainActivity;
import com.soundon.R;
import com.soundon.model.MusicList;
import com.soundon.model.MusicListSet;
import com.soundon.model.MusicUrl;
import com.soundon.util.JsonUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.soundon.MainActivity.musicList;

/**
 * Created by Vince on 2017/11/26.
 */

public class PlayActivity extends AppCompatActivity {

    public static SeekBar seekBar;
    public static TextView totalTime;
    public static TextView currentTime;
    private static TextView Title;
    private static TextView Artist;
    private ImageView button;
    public static ImageView lvPlay;
    private ImageView lvPre;
    private ImageView lvNext;
    private ImageView cover;
    private ImageView back;
    private MusicListSet musicListSet;
    private MusicList musicList;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_play);
        bind();
        init();
        updateSeekBar();
    }

    private void bind(){
        seekBar = (SeekBar)findViewById(R.id.sb_progress);
        totalTime = (TextView)findViewById(R.id.tv_total_time);
        currentTime = (TextView)findViewById(R.id.tv_current_time);
        Title = (TextView)findViewById(R.id.tv_title);
        Artist = (TextView)findViewById(R.id.tv_artist);
        cover = (ImageView)findViewById(R.id.iv_play_picture);
        back = (ImageView)findViewById(R.id.iv_back);
        button =(ImageView) findViewById(R.id.iv_list);
        lvPlay = (ImageView)findViewById(R.id.iv_play);
        lvPre = (ImageView)findViewById(R.id.iv_prev);
        lvNext = (ImageView)findViewById(R.id.iv_next);
    }

    private void init(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent4 =new Intent(PlayActivity.this,MusicListActivity.class);
                PlayActivity.this.startActivity(intent4);
            }
        });
        lvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicListSet = MusicListSet.getMusicListSet();
                musicList = musicListSet.getCurrentMusic();
                if(musicList != null && MainActivity.mediaPlayer != null){
                    if(MainActivity.mediaPlayer.isPlaying()){
                        MainActivity.mediaPlayer.pause();
                        lvPlay.setSelected(false);
                    }else {
                        MainActivity.mediaPlayer.start();
                        lvPlay.setSelected(true);
                        updateSeekBar();
                    }
                }else if(musicList != null && MainActivity.mediaPlayer == null){
                    lvPlay.setSelected(true);
                    new HttpTask().execute(musicList.getMusicId());
                    Glide.with(PlayActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(cover);
                }
            }
        });
        lvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicListSet = MusicListSet.getMusicListSet();
                if(musicListSet.getNextMusic() != null){
                    musicList = musicListSet.getPrevMusic();
                    musicListSet.setCurrentMusic(musicList.getMusicId());
                    musicListSet.setDBCurrent(musicList.getMusicId());
                    new HttpTask().execute(musicList.getMusicId());
                    Glide.with(PlayActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(cover);
                }
            }
        });
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicListSet = MusicListSet.getMusicListSet();
                if(musicListSet.getNextMusic() != null){
                    musicList = musicListSet.getNextMusic();
                    musicListSet.setCurrentMusic(musicList.getMusicId());
                    musicListSet.setDBCurrent(musicList.getMusicId());
                    new HttpTask().execute(musicList.getMusicId());
                    Glide.with(PlayActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(cover);
                }
            }
        });
//        musicListSet = MusicListSet.getMusicListSet();
//        if(musicListSet.getCurrentMusic() != null){
//            Title.setText(musicListSet.getCurrentMusic().getMusicName());
//            Artist.setText(musicListSet.getCurrentMusic().getArtistName());
//        }
    }

    static class HttpTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... idx){
            String id = idx[0];
            try{
                String Url = "http://39.106.8.190:3000/music/url?id=" + id;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();
                Response response = client.newCall(request).execute();
                if(response != null){
                    final String responseData = response.body().string();
                    JsonUtil.handleMusicUrlResponse(responseData);
                    MusicUrl musicUrl = MusicUrl.getMusicUrl();
                    if(MainActivity.mediaPlayer == null){
                        MainActivity.mediaPlayer = new MediaPlayer();
                        MainActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                MainActivity.imageViewPlay.setSelected(false);
                                PlayActivity.lvPlay.setSelected(false);
                                MainActivity.progressBar.setProgress(0);
                                PlayActivity.seekBar.setProgress(0);
                                PlayActivity.currentTime.setText("00:00");
                            }
                        });
                    }
                    MainActivity.mediaPlayer.reset();
                    MainActivity.mediaPlayer.setDataSource(musicUrl.getUrl());
                    MainActivity.mediaPlayer.prepare();
                    if (!MainActivity.mediaPlayer.isPlaying()) {
                        MainActivity.mediaPlayer.start();
                    }
                    return true;
                }
                return false;
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                Title.setText(MainActivity.musicList.getMusicName());
                Artist.setText(MainActivity.musicList.getArtistName());
                lvPlay.setSelected(true);
            }else {
                return;
            }
        }
    }

    //同步seekbar与进度条时间
    private void updateSeekBar(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.mediaPlayer != null && MainActivity.mediaPlayer.isPlaying()){
                    int mCurrentPosition = MainActivity.mediaPlayer.getCurrentPosition() / 1000;//获取player当前进度，毫秒表示
                    int total = MainActivity.mediaPlayer.getDuration() / 1000;//获取当前歌曲总时长
                    seekBar.setProgress(mCurrentPosition);//seekbar同步歌曲进度
                    seekBar.setMax(total);//seekbar设置总时长
                    totalTime.setText(calculateTime(total));
                    currentTime.setText(calculateTime(mCurrentPosition));
                    lvPlay.setSelected(true);
                }
                mHandler.postDelayed(this,1000);//延迟一秒运行线程
            }
        });
    }

    //计算歌曲时间
    private String calculateTime(int time){
        int minute = 0;
        int second = 0;
        if (time >= 60){
            minute = time / 60;
            second = time % 60;
        }else if (time < 60){
            second = time;
        }
        if (minute < 10 && second < 10){
            return "0" + minute + ":0" +second;
        }else if(minute < 10){
            return "0" + minute + ":" + second;
        }else if(second < 10){
            return minute + ":0" +second;
        }
        return minute + ":" +second;
    }

    @Override
    protected void onResume(){
        super.onResume();
        musicListSet = MusicListSet.getMusicListSet();
        musicList = musicListSet.getCurrentMusic();
        if(musicList != null){
            Title.setText(musicList.getMusicName());
            Artist.setText(musicList.getArtistName());
            Glide.with(PlayActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(cover);
        }
        updateSeekBar();
    }


}
