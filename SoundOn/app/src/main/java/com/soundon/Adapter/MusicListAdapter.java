package com.soundon.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soundon.MainActivity;
import com.soundon.R;
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

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MusicList> musicLists;
    private MusicListSet musicListSet = MusicListSet.getMusicListSet();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View musicListView;
        TextView musicListName;
        TextView musicListInfo;
        ImageView musicListDelete;

        public ViewHolder(View view) {
            super(view);
            musicListView = view;
            musicListName = (TextView) view.findViewById(R.id.musicList_name);
            musicListInfo = (TextView) view.findViewById(R.id.musicList_singer);
            musicListDelete = (ImageView) view.findViewById(R.id.iv_delete);
        }
    }

    public MusicListAdapter(Context context,ArrayList<MusicList> musicLists){
        this.context = context;
        this.musicLists = musicLists;
    }

    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.musiclist_item, parent, false);
        final MusicListAdapter.ViewHolder holder = new MusicListAdapter.ViewHolder(view);
        holder.musicListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                musicListSet = MusicListSet.getMusicListSet();
                final MusicList musicList = musicListSet.get(position);
                try {
                    String Url = "http://39.106.8.190:3000/music/url?id=" + musicList.getMusicId();
                    HttpUtil.sendOkHttpRequest(Url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到服务器返回的具体内容
                            final String responseData = response.body().string();
                            JsonUtil.handleMusicUrlResponse(responseData);
                            final MusicUrl musicUrl = MusicUrl.getMusicUrl();
                            if (musicUrl != null && !musicUrl.getUrl().equals("null")) {
                                musicListSet.setCurrentMusic(musicList.getMusicId());
                                musicListSet.setDBCurrent(musicList.getMusicId());
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
                Toast.makeText(view.getContext(), "正在播放：" + musicList.getMusicName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.musicListDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posotion = holder.getAdapterPosition();
                musicListSet = MusicListSet.getMusicListSet();
                MusicList musicList = musicLists.get(posotion);
                if(musicListSet.getCurrentMusic() == musicList){
                    Toast.makeText(view.getContext(),"该音乐为当前播放的音乐！！！",Toast.LENGTH_SHORT).show();
                }else{
                    musicListSet.deleteOne(musicList.getMusicId());
                    musicListSet.deleteOneFromDB(musicList.getMusicId());
                    notifyItemRemoved(posotion);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MusicList musicList = musicLists.get(position);
        if(musicList != null){
            holder.musicListName.setText(musicList.getMusicName());
            holder.musicListInfo.setText(musicList.getArtistName() + " - " + musicList.getAlbumName());
        }
    }

    @Override
    public int getItemCount() {
        return musicLists.size();
    }

}
