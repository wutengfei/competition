package com.xzq.weatherofxia;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xzq.weatherofxia.bean.City;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by lenovo on 2017/11/20.
 */

/**
 * 用于展示省市的适配器
 */
public class cityAdapter extends BaseAdapter {//适配器，用于给listview传递信息
    private ArrayList<City> citys;//City类泛型集合，接收传递进来的citys
    private Context mContext;
    public cityAdapter(ArrayList<City> citys,Context mContext){//构造方法，先执行，获得传递来的值
        this.citys=citys;
       // System.out.println("瞎子去"+citys.size());
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
       // System.out.println("citys.size"+citys.size());
        return citys.size();//返回citys的大小，用于确定ListView的长度
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
            convertView= View.inflate(mContext,R.layout.item,null);
        }//如果缓存convertView为空，则创建新的View,防止列表多的时候内存占用过大
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        tv.setText(citys.get(position).name);//设置列表名称
        return convertView;//返回一个View类型的
    }
}
