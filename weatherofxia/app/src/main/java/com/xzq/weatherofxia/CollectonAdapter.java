package com.xzq.weatherofxia;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xzq.weatherofxia.bean.myCollection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lenovo on 2017/11/26.
 */

public class CollectonAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<myCollection> collections;
    public CollectonAdapter(Context mContext, ArrayList<myCollection> collections){
        this.mContext=mContext;
        this.collections=collections;
    }
    @Override
    public int getCount() {
        System.out.println("az在"+collections.size());
        return collections.size();
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
            convertView= View.inflate(mContext,R.layout.item_collection,null);
        }//如果缓存convertView为空，则创建新的View,防止列表多的时候内存占用过大
        TextView tv = (TextView) convertView.findViewById(R.id.tv_collection);
        tv.setText(collections.get(position).cityname);//设置列表名称
        return convertView;//返回一个View类型的
    }
}
