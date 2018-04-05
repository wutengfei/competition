package com.soundon.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.soundon.MainActivity;
import com.soundon.R;
import com.soundon.model.AlbumMusic;
import com.soundon.model.MusicList;
import com.soundon.model.MusicListSet;
import com.soundon.model.MusicUrl;
import com.soundon.util.HttpUtil;
import com.soundon.util.JsonUtil;
import com.soundon.view.PlayActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Vince on 2017/11/28.
 */

public class AlbumMusicAdapter extends RecyclerView.Adapter<AlbumMusicAdapter.ViewHolder>{
    private ArrayList<AlbumMusic> albumMusics;
    private MusicListSet musicListSet;
    private String albumName;
    private Context mContext;
    static  class ViewHolder extends  RecyclerView.ViewHolder {
        View cardView;
        TextView albumMusicName;
        TextView albumMusicArtist;

        public ViewHolder(View view) {
            super(view);
            cardView =(View) view;
            albumMusicName = (TextView) view.findViewById(R.id.albumMusic_name);
            albumMusicArtist = (TextView) view.findViewById(R.id.albumMusic_singer);
        }
    }
    public AlbumMusicAdapter(Context context,String albumName,ArrayList<AlbumMusic> albumMusics) {
        this.mContext = context;
        this.albumName = albumName;
        this.albumMusics = albumMusics;
    }

    @Override
    public AlbumMusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        if(mContext == null) {
            mContext= parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent,false);
        final AlbumMusicAdapter.ViewHolder holder =new AlbumMusicAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final AlbumMusic albumMusic = albumMusics.get(position);
                musicListSet = MusicListSet.getMusicListSet();
                try {
                    String Url = "http://39.106.8.190:3000/music/url?id=" + albumMusic.getMusicId();
                    HttpUtil.sendOkHttpRequest(Url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到服务器返回的具体内容
                            final String responseData = response.body().string();
                            JsonUtil.handleMusicUrlResponse(responseData);
                            final MusicUrl musicUrl = MusicUrl.getMusicUrl();
                            if (musicUrl != null && !musicUrl.getUrl().equals("null")) {
                                MusicList musicList = new MusicList();
                                musicList.setMusicId(albumMusic.getMusicId());
                                musicList.setMusicName(albumMusic.getMusicName());
                                musicList.setArtistName(albumMusic.getArtistName());
                                musicList.setAlbumName(albumName);
                                musicList.setAlbumPic(albumMusic.getAlbumPic());
                                musicListSet = MusicListSet.getMusicListSet();
                                musicListSet.addOneToTheNext(musicList);
                                musicListSet.setCurrentMusic(musicList.getMusicId());
                                musicListSet.saveToDB(musicList);
                                musicListSet.setDBCurrent(albumMusic.getMusicId());
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
                            }
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "正在播放：" + albumMusic.getMusicName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumMusicAdapter.ViewHolder holder, int position) {
        AlbumMusic albumMusic  = albumMusics.get(position);
        holder.albumMusicName.setText(albumMusic.getMusicName());
        holder.albumMusicArtist.setText(albumMusic.getArtistName());
    }

    @Override
    public  int getItemCount() {
        return  albumMusics.size();
    }
}
