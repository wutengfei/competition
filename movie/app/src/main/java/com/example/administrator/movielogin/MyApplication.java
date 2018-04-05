package com.example.administrator.movielogin;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/5.
 */

public class MyApplication extends Application {
    private String name = null;
    private ArrayList<String> user_name = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_name(String user_name) {
        this.user_name.add(user_name);
    }

    public String getUser_name(int i) {
        return user_name.get(i);
    }

    public int getSize() {
        return user_name.size();
    }

    public Boolean remove(int i) {
        if (user_name.remove(getUser_name(i))) {
            return true;
        }
        return false;
    }
}
