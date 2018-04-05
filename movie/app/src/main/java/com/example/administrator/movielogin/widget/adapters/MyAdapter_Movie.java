package com.example.administrator.movielogin.widget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.movielogin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Howe on 2017/1/28.
 */

public class MyAdapter_Movie extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public MyAdapter_Movie(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.movie_listview, null);
            viewHolder.mTextView = (TextView) view.findViewById(R.id.moviename);
            viewHolder.mButton = (Button) view.findViewById(R.id.btnmoviebuy);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(mList.get(i));
        viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnItemDeleteListener.onDeleteClick(i);
                mOnItemGetListener.onGetClick(i);
            }
        });
        return view;
    }

    /**
     * 跳转按钮的监听接口
     */

    public interface onItemGetListener{
        void onGetClick(int i);
    }

    private onItemGetListener mOnItemGetListener;

    public void setOnItemGetClickListener(onItemGetListener mOnItemGetListener){
        this.mOnItemGetListener = mOnItemGetListener;
    }

    class ViewHolder {

        TextView mTextView;
        Button mButton;
    }

}
