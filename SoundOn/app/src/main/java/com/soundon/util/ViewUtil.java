package com.soundon.util;

import android.view.View;

/**
 * Created by Vince on 2017/11/25.
 */

public class ViewUtil {
    public static final int LOADING = 0;
    public static final int LOAD_SUCCESS = 1;
    public static final int LOAD_FAILED = 2;
    public static void changeViewState(View loadSuccess, View loading, View loadFailed, int state) {
        switch (state) {
            case LOADING:
                loadSuccess.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loadFailed.setVisibility(View.GONE);
                break;
            case LOAD_SUCCESS:
                loadSuccess.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                loadFailed.setVisibility(View.GONE);
                break;
            case LOAD_FAILED:
                loadSuccess.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                loadFailed.setVisibility(View.VISIBLE);
                break;
        }
    }
}
