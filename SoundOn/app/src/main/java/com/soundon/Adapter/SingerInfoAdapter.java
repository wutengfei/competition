package com.soundon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soundon.R;
import com.soundon.model.AlbumInfo;
import com.soundon.view.AlbumMusicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 2017/11/28.
 */

public class SingerInfoAdapter  extends RecyclerView.Adapter<SingerInfoAdapter.ViewHolder> {
    private ArrayList<AlbumInfo> albumInfos;
    private Context mContext;
    static  class ViewHolder extends  RecyclerView.ViewHolder {
        View cardView;
        ImageView albumImage;
        TextView albumName;

        public ViewHolder(View view) {
            super(view);
            cardView =(View) view;
            albumImage=(ImageView) view.findViewById(R.id.album_image);
            albumName=(TextView) view.findViewById(R.id.album_name);
        }
    }
    public SingerInfoAdapter(Context context,ArrayList<AlbumInfo> albumInfos) {
        this.mContext = context;
        this.albumInfos = albumInfos;
    }

    @Override
    public SingerInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType) {
        if(mContext == null) {
            mContext= parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.singer_info_item, parent,false);
        final SingerInfoAdapter.ViewHolder holder =new SingerInfoAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(mContext, AlbumMusicActivity.class);
                intent.putExtra("idx",albumInfos.get(position).getAlbumId());
                intent.putExtra("alName",albumInfos.get(position).getAlbumName());
                intent.putExtra("alPic",albumInfos.get(position).getAlbumName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SingerInfoAdapter.ViewHolder holder, int position) {
        AlbumInfo albumInfo  = albumInfos.get(position);
        holder.albumName.setText(albumInfo.getAlbumName());
        Glide.with(mContext).load(albumInfo.getAlbumPic()).error(R.drawable.default_artist).into(holder.albumImage);
    }

    @Override
    public  int getItemCount() {
        return  albumInfos.size();
    }

}
