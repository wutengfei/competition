package com.xzq.weatherofxia;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/11/25.
 */

/**
 * 用于展示天气信息的适配器
 */
public class WeatherAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<StringBuilder> sbs;
    public WeatherAdapter(Context mContext, ArrayList<StringBuilder> sbs){
        this.mContext=mContext;
        this.sbs=sbs;
    }
    @Override
    public int getCount() {
        return sbs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= View.inflate(mContext,R.layout.item_weather,null);
        }//如果缓存convertView为空，则创建新的View,防止列表多的时候内存占用过大
        TextView tv = (TextView) convertView.findViewById(R.id.tv_weather);
        tv.setText(sbs.get(position).toString());//设置列表名称
        return convertView;//返回一个View类型的
    }
}
