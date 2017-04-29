package com.android.util.net.http;

/**
 * Created by lemon on 2017/3/13.
 */

public interface HttpCallbackListener {
    void onFinish(String resp);

    void onError(Exception e);
}
