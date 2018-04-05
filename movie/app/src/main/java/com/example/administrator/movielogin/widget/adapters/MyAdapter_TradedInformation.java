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


public class MyAdapter_TradedInformation extends BaseAdapter {
    private Context mContext;
    private List<String> list_id = new ArrayList<>();
    private List<String> list_movie = new ArrayList<>();
    private List<String> list_date = new ArrayList<>();
    private List<String> list_cinema = new ArrayList<>();
    private List<String> list_number = new ArrayList<>();
    private List<String> list_price = new ArrayList<>();
    private List<String> list_seat = new ArrayList<>();

    public MyAdapter_TradedInformation(Context context, List<String> id,
                                 List<String> movie,List<String> date,List<String> cinema,
                                       List<String> price,List<String> number,List<String> seat) {
        mContext = context;
        list_id = id;
        list_movie = movie;
        list_date = date;
        list_cinema = cinema;
        list_number = number;
        list_price = price;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.traded_information, null);
            viewHolder.mTextView_id = (TextView) view.findViewById(R.id.showdingdan);
            viewHolder.mTextView_cinema = (TextView) view.findViewById(R.id.cinemaname);
            viewHolder.mTextView_movie = (TextView) view.findViewById(R.id.moviename);
            viewHolder.mTextView_date = (TextView) view.findViewById(R.id.moviedate);
            viewHolder.mTextView_seat = (TextView) view.findViewById(R.id.movieseat);
            viewHolder.mTextView_price = (TextView) view.findViewById(R.id.movieprice);
            viewHolder.mTextView_number = (TextView) view.findViewById(R.id.movienumber);
            viewHolder.mButton = (Button) view.findViewById(R.id.tickdelete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView_id.setText(list_id.get(i));
        viewHolder.mTextView_movie.setText(list_movie.get(i));
        viewHolder.mTextView_cinema.setText(list_cinema.get(i));
        viewHolder.mTextView_date.setText(list_date.get(i));
        viewHolder.mTextView_seat.setText("座位号："+list_seat.get(i));
        viewHolder.mTextView_price.setText("价格："+list_price.get(i)+"元");
        viewHolder.mTextView_number.setText("数量："+list_number.get(i));


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

        TextView mTextView_id;
        TextView mTextView_movie;
        TextView mTextView_date;
        TextView mTextView_seat;
        TextView mTextView_price;
        TextView mTextView_cinema;
        TextView mTextView_number;
        Button mButton;
    }

}
