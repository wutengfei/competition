package com.example.five_two;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 璐 on 2017/12/3.
 */

public class MyApplication extends Application {

    private static boolean isProgramExit = false;

    public void setExit(boolean exit) {
        isProgramExit = exit;
        }

    public boolean isExit() {
        return isProgramExit;

    }

}

