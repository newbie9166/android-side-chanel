package com.android.util.net.http;

/**
 * Created by lemon on 2017/3/13.
 */

import android.util.Log;

public class LogdListener implements HttpCallbackListener {
    @Override
    public void onFinish(String resp) {
        Log.d("GET", "onFinish: " + resp);
    }

    @Override
    public void onError(Exception e) {
        Log.d("GET", "onError: " + e);
    }
}
