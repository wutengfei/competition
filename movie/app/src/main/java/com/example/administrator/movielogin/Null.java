package com.example.administrator.movielogin;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
//import com.example.bottomtabbar.BottomTabBar;

public class Null extends FragmentActivity {
//    private BottomTabBar mBottomBar;

    private movie movieFragment;
    private cinema cinemaFragment;
    private user userFragment;

    private int currentId = R.id.lmovie;// 当前选中id,默认是主页

    private TextView tvmovie, tvcinema, tvuser;//底部四个TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        tvmovie = (TextView) findViewById(R.id.lmovie);
        tvmovie.setSelected(true);//首页默认选中
        tvcinema = (TextView)findViewById(R.id.lcinema);
        tvuser = (TextView) findViewById(R.id.luser);

        /**
         * 默认加载首页
         */
        movieFragment = new movie();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, movieFragment).commit();

        tvmovie.setOnClickListener(tabClickListener);
        tvcinema.setOnClickListener(tabClickListener);
        tvuser.setOnClickListener(tabClickListener);
    }

    private View.OnClickListener tabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() != currentId) {//如果当前选中跟上次选中的一样,不需要处理
                changeSelect(v.getId());//改变图标跟文字颜色的选中
                changeFragment(v.getId());//fragment的切换
                currentId = v.getId();//设置选中id
            }
        }
    };

    /**
     * 改变fragment的显示
     *
     * @param resId
     */
    private void changeFragment(int resId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//开启一个Fragment事务

        hideFragments(transaction);//隐藏所有fragment
        if(resId==R.id.lmovie){//主页
            if(movieFragment==null){//如果为空先添加进来.不为空直接显示
                movieFragment = new movie();
                transaction.add(R.id.main_container,movieFragment);
            }else {
                transaction.show(movieFragment);
            }
        }else if(resId==R.id.lcinema){//动态
            if(cinemaFragment==null){
                cinemaFragment = new cinema();
                transaction.add(R.id.main_container,cinemaFragment);
            }else {
                transaction.show(cinemaFragment);
            }
        }else if(resId==R.id.luser){//消息中心
            if(userFragment==null){
                userFragment = new user();
                transaction.add(R.id.main_container,userFragment);
            }else {
                transaction.show(userFragment);
            }
        }
        transaction.commit();//一定要记得提交事务
    }

    /**
     * 显示之前隐藏所有fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction){
//        if (mainFragment != null)//不为空才隐藏,如果不判断第一次会有空指针异常
//            transaction.hide(mainFragment);
        if (cinemaFragment != null)
            transaction.hide(cinemaFragment);
        if (userFragment != null)
            transaction.hide(userFragment);
        if (movieFragment != null)
            transaction.hide(movieFragment);
    }

    /**
     * 改变TextView选中颜色
     * @param resId
     */
    private void changeSelect(int resId) {
        tvmovie.setSelected(false);
        tvcinema.setSelected(false);
        tvuser.setSelected(false);

        switch (resId) {
            case R.id.lmovie:
                tvmovie.setSelected(true);
                break;
            case R.id.lcinema:
                tvcinema.setSelected(true);
                break;
            case R.id.luser:
                tvuser.setSelected(true);
                break;
        }
    }
}
