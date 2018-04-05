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

public class MyAdapter_information extends BaseAdapter {
    private Context mContext;
    private List<String> list_id = new ArrayList<>();
    private List<String> list_movie = new ArrayList<>();
    private List<String> list_date = new ArrayList<>();
    private List<String> list_cinema = new ArrayList<>();
    private List<String> list_user = new ArrayList<>();
    private List<String> list_price = new ArrayList<>();
    private List<String> list_number = new ArrayList<>();
    private List<String> list_seat = new ArrayList<>();
    private List<String> list_phone = new ArrayList<>();

    public MyAdapter_information(Context context, List<String> id,
                                 List<String> movie,List<String> date,
                                 List<String> cinema,List<String> user,List<String> phone,
                                 List<String> price,List<String> number, List<String> seat) {
        mContext = context;
        list_id = id;
        list_movie = movie;
        list_date = date;
        list_cinema = cinema;
        list_user = user;
        list_phone = phone;
        list_price = price;
        list_number = number;
        list_seat = seat;
    }

    @Override
    public int getCount() {
        return list_id.size();
    }

    @Override
    public Object getItem(int i) {
        return list_id.get(i);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.information_listview, null);
            viewHolder.mTextView_id = (TextView) view.findViewById(R.id.showid);
            viewHolder.mTextView_cinema = (TextView) view.findViewById(R.id.showcinema);
            viewHolder.mTextView_movie = (TextView) view.findViewById(R.id.showmoviename);
            viewHolder.mTextView_date = (TextView) view.findViewById(R.id.showdate);
            viewHolder.mTextView_seat = (TextView) view.findViewById(R.id.showseat);
            viewHolder.mTextView_price = (TextView) view.findViewById(R.id.showprice);
            viewHolder.mTextView_number = (TextView) view.findViewById(R.id.shownumber);
            viewHolder.mTextView_user = (TextView) view.findViewById(R.id.showuser);
            viewHolder.mButton_chat = (Button) view.findViewById(R.id.chat);
            viewHolder.mButton_phone = (Button) view.findViewById(R.id.phone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView_id.setText(list_id.get(i));
        viewHolder.mTextView_movie.setText(list_movie.get(i));
        viewHolder.mTextView_cinema.setText(list_cinema.get(i));
        viewHolder.mTextView_date.setText(list_date.get(i));
        viewHolder.mTextView_seat.setText("座位号："+list_seat.get(i));
        viewHolder.mTextView_number.setText("数量："+list_number.get(i));
        viewHolder.mTextView_price.setText("价格:"+list_price.get(i)+"元");
        viewHolder.mTextView_user.setText("出票人："+list_user.get(i));


        viewHolder.mButton_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnItemDeleteListener.onDeleteClick(i);
                mOnItemGetListener_chat.onGetClick(i);
            }
        });

        viewHolder.mButton_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnItemDeleteListener.onDeleteClick(i);
                mOnItemGetListener_phone.onGetClick(i);
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

    private onItemGetListener mOnItemGetListener_chat,mOnItemGetListener_phone;

    public void setOnItemGetClickListener_chat(onItemGetListener mOnItemGetListener){
        this.mOnItemGetListener_chat = mOnItemGetListener;
    }

    public void setOnItemGetClickListener_phone(onItemGetListener mOnItemGetListener){
        this.mOnItemGetListener_phone = mOnItemGetListener;
    }

    class ViewHolder {

        TextView mTextView_id;
        TextView mTextView_movie;
        TextView mTextView_date;
        TextView mTextView_seat;
        TextView mTextView_number;
        TextView mTextView_price;
        TextView mTextView_cinema;
        TextView mTextView_user;
        Button mButton_chat;
        Button mButton_phone;
    }

}
