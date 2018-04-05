package com.soundon.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
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
import com.soundon.model.SearchMusic;
import com.soundon.util.HttpUtil;
import com.soundon.util.JsonUtil;
import com.soundon.view.AlbumMusicActivity;
import com.soundon.view.PlayActivity;
import com.soundon.view.SingerInfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static java.lang.Thread.sleep;

/**
 * Created by Vince on 2017/11/26.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchMusic> mSearchMusicList;
    private MusicListSet musicListSet = MusicListSet.getMusicListSet();
    private String[] itemList={"查看歌手信息","查看专辑信息"};

    static class ViewHolder extends RecyclerView.ViewHolder {
        View searchMusicView;
        TextView searchMusicName;
        TextView searchMusicInfo;
        ImageView musicMore;

        public ViewHolder(View view) {
            super(view);
            searchMusicView = view;
            searchMusicName = (TextView) view.findViewById(R.id.music_name);
            searchMusicInfo = (TextView) view.findViewById(R.id.music_singer);
            musicMore = (ImageView) view.findViewById(R.id.iv_more);
        }
    }

    public PlayListAdapter(Context context,ArrayList<SearchMusic> searchMusicList){
        this.context = context;
        this.mSearchMusicList = searchMusicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.searchMusicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                final SearchMusic searchMusic = mSearchMusicList.get(position);
                try {
                    String Url = "http://39.106.8.190:3000/music/url?id=" + searchMusic.getMusicId();
                    HttpUtil.sendOkHttpRequest(Url, new okhttp3.Callback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //得到服务器返回的具体内容
                            final String responseData = response.body().string();
                            JsonUtil.handleMusicUrlResponse(responseData);
                            final MusicUrl musicUrl = MusicUrl.getMusicUrl();
                            if (musicUrl != null && !musicUrl.getUrl().equals("null")) {
                                MusicList musicList = new MusicList();
                                musicList.setMusicId(searchMusic.getMusicId());
                                musicList.setMusicName(searchMusic.getMusicName());
                                musicList.setArtistName(searchMusic.getArtistName());
                                musicList.setAlbumName(searchMusic.getAlbumName());
                                musicList.setAlbumPic(searchMusic.getAlbumPic());
                                musicListSet = MusicListSet.getMusicListSet();
                                musicListSet.addOneToTheNext(musicList);
                                musicListSet.setCurrentMusic(musicList.getMusicId());
                                musicListSet.saveToDB(musicList);
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
                Toast.makeText(v.getContext(), "正在播放：" + searchMusic.getMusicName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.musicMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final int position = holder.getAdapterPosition();
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setItems(itemList,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (itemList[which]) {
                                    case "查看歌手信息" :
                                        Intent intent10 = new Intent(context, SingerInfoActivity.class);
                                        intent10.putExtra("idx",mSearchMusicList.get(position).getArtistId());
                                        intent10.putExtra("arName",mSearchMusicList.get(position).getArtistName());
                                        intent10.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent10);
                                        break;
                                    case "查看专辑信息":
                                        Intent intent11 = new Intent(context, AlbumMusicActivity.class);
                                        intent11.putExtra("idx",mSearchMusicList.get(position).getAlbumId());
                                        intent11.putExtra("alName",mSearchMusicList.get(position).getAlbumName());
                                        intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent11);
                                        break;
                                }
                            }
                        });
                dialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchMusic searchMusic = mSearchMusicList.get(position);
        holder.searchMusicName.setText(searchMusic.getMusicName());
        holder.searchMusicInfo.setText(searchMusic.getArtistName() + " - " + searchMusic.getAlbumName());
    }

    @Override
    public int getItemCount() {
        return mSearchMusicList.size();
    }
}
