package com.soundon.Adapter;

import android.app.Activity;
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
import com.soundon.model.TopList;
import com.soundon.view.TopListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 2017/11/26.
 */

public class TopListAdapter  extends RecyclerView.Adapter<TopListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<TopList> mTopListSet;

    static  class ViewHolder extends  RecyclerView.ViewHolder {
        CardView cardView;
        ImageView topImage;
        TextView topName;

        public ViewHolder(View view) {
            super(view);
            cardView =(CardView) view;
            topImage=(ImageView) view.findViewById(R.id.top_list_image);
            topName=(TextView) view.findViewById(R.id.top_list_name);
        }
    }
    public TopListAdapter(Context context,ArrayList<TopList> topListSet) {
        this.context = context;
        mTopListSet = topListSet;
    }

    @Override
    public TopListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toplist_item, parent, false);
        final TopListAdapter.ViewHolder holder = new TopListAdapter.ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context,TopListActivity.class);
                intent.putExtra("idx",mTopListSet.get(position).getIdx());
                intent.putExtra("title",mTopListSet.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TopListAdapter.ViewHolder holder1, int position)
    {
        TopList topList  = mTopListSet.get(position);
        holder1.topName.setText(topList.getName());
        Glide.with(context).load(topList.getPic()).error(R.drawable.default_cover).into(holder1.topImage);
    }

    @Override
    public  int getItemCount() {
        return  mTopListSet.size();
    }

}
