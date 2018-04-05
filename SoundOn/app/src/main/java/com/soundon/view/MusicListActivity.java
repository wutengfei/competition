package com.soundon.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.soundon.Adapter.MusicListAdapter;
import com.soundon.R;
import com.soundon.model.MusicListSet;

public class MusicListActivity extends AppCompatActivity {

    private static MusicListSet musicListSet;
    private static RecyclerView recyclerView;
    private static LinearLayout llLoading;
    private static LinearLayout llLoadFail;
    private static MusicListAdapter adapter;
    private static ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        musicListSet = MusicListSet.getMusicListSet();
        recyclerView = (RecyclerView) findViewById(R.id.musicList_recycler);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llLoadFail = (LinearLayout) findViewById(R.id.ll_load_fail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imageViewBack = (ImageView)findViewById(R.id.iv_my_back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MusicListAdapter(this,musicListSet);
        recyclerView.setAdapter(adapter);
    }
}
