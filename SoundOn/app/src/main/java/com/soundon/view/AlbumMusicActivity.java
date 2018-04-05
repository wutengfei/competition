package com.soundon.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundon.Adapter.AlbumMusicAdapter;
import com.soundon.Adapter.SingerInfoAdapter;
import com.soundon.R;
import com.soundon.model.AlbumMusic;
import com.soundon.model.AlbumMusicList;
import com.soundon.util.JsonUtil;
import com.soundon.util.ViewUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlbumMusicActivity extends AppCompatActivity {

    private static AlbumMusicList albumMusicList;
    private static RecyclerView recyclerView;
    private static LinearLayout llLoading;
    private static LinearLayout llLoadFail;
    private static AlbumMusicAdapter adapter;
    private static TextView textView;
    private static ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);
        Intent intent = getIntent();
        String albumId = (String) intent.getSerializableExtra("idx");
        String albumName = (String) intent.getSerializableExtra("alName");
        albumMusicList = AlbumMusicList.getAlbumMusicList();
        recyclerView = (RecyclerView)findViewById(R.id.album_recycler);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llLoadFail = (LinearLayout) findViewById(R.id.ll_load_fail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AlbumMusicAdapter(this,albumName,albumMusicList);
        recyclerView.setAdapter(adapter);
        textView = (TextView)findViewById(R.id.al_name);
        textView.setText(albumName);
        imageViewBack = (ImageView)findViewById(R.id.iv_my_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new AlbumMusicActivity.HttpTask().execute(albumId);
    }

    class HttpTask extends AsyncTask<String, Void, Integer> {

        public static final int LOADING = 0;
        public static final int LOAD_SUCCESS = 1;
        public static final int LOAD_FAILED = 2;

        @Override
        protected void onPreExecute(){
            ViewUtil.changeViewState(recyclerView,llLoading,llLoadFail,LOADING);
        }

        @Override
        protected Integer doInBackground(String... idx){
            String id = idx[0];
            try{
                String Url = "http://39.106.8.190:3000/album?id=" + id;
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Url)
                        .build();
                Response response = client.newCall(request).execute();
                if(response != null){
                    final String responseData = response.body().string();
                    JsonUtil.handleAlbumMusicResponse(responseData);
                    return LOAD_SUCCESS;
                }
                return LOAD_FAILED;
            }catch (Exception e){
                e.printStackTrace();
            }
            return LOAD_FAILED;
        }

        @Override
        protected void onPostExecute(Integer result){
            if(result == LOAD_SUCCESS){
                adapter.notifyDataSetChanged();
                ViewUtil.changeViewState(recyclerView,llLoading,llLoadFail,LOAD_SUCCESS);
            }else if(result == LOAD_FAILED){
                ViewUtil.changeViewState(recyclerView,llLoading,llLoadFail,LOAD_FAILED);
            }
        }
    }

}
