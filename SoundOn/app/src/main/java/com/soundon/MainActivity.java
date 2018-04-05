package com.soundon;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundon.Adapter.TopListAdapter;
import com.soundon.model.MusicList;
import com.soundon.model.MusicListSet;
import com.soundon.model.MusicUrl;
import com.soundon.model.TopListSet;
import com.soundon.util.JsonUtil;
import com.soundon.view.AboutActivity;
import com.soundon.view.PlayActivity;
import com.soundon.view.SearchMusicActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    private static MusicUrl musicUrl;
    private static TopListSet topListSet;
    private static RecyclerView recyclerView;
    private static TopListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    FrameLayout frameLayout;
    public static ImageView imageViewPlay;
    private static ImageView imageViewPic;
    private static ImageView imageViewNext;
    private static TextView textViewTitle;
    private static TextView textViewArtist;
    public static ProgressBar progressBar;
    private  Handler mHandler = new Handler();
    private static MusicListSet musicListSet;
    public static MusicList musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topListSet = TopListSet.getTopListSet();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TopListAdapter(this,topListSet);
        recyclerView.setAdapter(adapter);
        musicListSet = MusicListSet.getMusicListSet();
        musicList = musicListSet.getCurrentMusic();
        bindAll();
        init();
        if(musicList != null){
            Glide.with(MainActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(imageViewPic);
        }
        updatePlayBar();
    }
    public void bindAll(){
        frameLayout = (FrameLayout) findViewById(R.id.fl_play_bar);
        imageViewPlay = (ImageView) findViewById(R.id.iv_play_bar_play);
        imageViewPic = (ImageView) findViewById(R.id.iv_play_bar_cover);
        imageViewNext = (ImageView) findViewById(R.id.iv_play_bar_next);
        textViewTitle = (TextView) findViewById(R.id.tv_play_bar_title);
        textViewArtist = (TextView) findViewById(R.id.tv_play_bar_artist);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        progressBar = (ProgressBar) findViewById(R.id.pb_play_bar);
    }
    public void init(){

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, PlayActivity.class);
//                intent3.putExtra("musicName",musicList.)
                MainActivity.this.startActivity(intent3);
            }
        });

        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicListSet = MusicListSet.getMusicListSet();
                musicList = musicListSet.getCurrentMusic();
                if(musicList != null && mediaPlayer != null){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        imageViewPlay.setSelected(false);
                    }else {
                        mediaPlayer.start();
                        imageViewPlay.setSelected(true);
                        updatePrograssBar();
                    }
                }else if(musicList != null && mediaPlayer == null){
                    imageViewPlay.setSelected(true);
                    new HttpTask().execute(musicList.getMusicId());
                    Glide.with(MainActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(imageViewPic);
                }
            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicListSet = MusicListSet.getMusicListSet();
                if(musicListSet.getNextMusic() != null){
                    musicList = musicListSet.getNextMusic();
                    musicListSet.setCurrentMusic(musicList.getMusicId());
                    musicListSet.setDBCurrent(musicList.getMusicId());
                    new HttpTask().execute(musicList.getMusicId());
                    Glide.with(MainActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(imageViewPic);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        ActionBar actionBar = getSupportActionBar();//得到actionbar的实例
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置导航按钮图标
        }

        navView.setCheckedItem(R.id.nav_about);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_about:
//                        //   Toast.makeText(this, "you clicked nav_about", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                        MainActivity.this.startActivity(intent2);
                        break;

                    case R.id.nav_exit:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("提示：");
                        dialog.setMessage("退出应用程序？");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public void updatePlayBar(){
        musicList = musicListSet.getCurrentMusic();
        if(musicList != null){
            textViewTitle.setText(musicList.getMusicName());
            textViewArtist.setText(musicList.getArtistName() +  " - " + musicList.getAlbumName());
            updatePrograssBar();
            if(mediaPlayer != null && mediaPlayer.isPlaying()){
                imageViewPlay.setSelected(true);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new  Intent(MainActivity.this,SearchMusicActivity.class);
                intent.putExtra("keyWords",query);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
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
                    musicUrl = MusicUrl.getMusicUrl();
                    if(mediaPlayer == null){
                        mediaPlayer = new MediaPlayer();
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
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicUrl.getUrl());
                    mediaPlayer.prepare();
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
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
                textViewTitle.setText(musicList.getMusicName());
                textViewArtist.setText(musicList.getArtistName() +  " - " + musicList.getAlbumName());
                imageViewPlay.setSelected(true);
            }else {
                return;
            }
        }
    }

    public void updatePrograssBar(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null  && mediaPlayer.isPlaying()){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;//获取player当前进度，毫秒表示
                    int total = mediaPlayer.getDuration() / 1000;//获取当前歌曲总时长
                    progressBar.setProgress(mCurrentPosition);//seekbar同步歌曲进度
                    progressBar.setMax(total);//seekbar设置总时长
                    imageViewPlay.setSelected(true);
                }
                mHandler.postDelayed(this,1000);//延迟一秒运行线程
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        musicListSet = MusicListSet.getMusicListSet();
        musicList = musicListSet.getCurrentMusic();
        if(musicList != null){
            Glide.with(MainActivity.this).load(musicList.getAlbumPic()).error(R.drawable.default_cover).into(imageViewPic);
        }
        imageViewPlay.setSelected(false);
        updatePlayBar();
    }


}
