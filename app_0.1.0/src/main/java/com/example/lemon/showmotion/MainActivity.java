package com.example.lemon.showmotion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.Info;
import com.android.proc.MemInfo;
import com.android.sensor.SensorInfo;
import com.android.util.net.http.HttpUtil;
import com.android.util.net.http.LogdListener;

public class MainActivity extends AppCompatActivity {
    private static final String address = "http://192.168.1.108:8080";
//    private Info info = new MemInfo();
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        info = new SensorInfo(this);

        String infostr = this.info.update();
        Log.d("MinActivity", "onResume: " + infostr);
        LogdListener listener = new LogdListener();
        HttpUtil.httpGetRequest(address, listener);
        HttpUtil.httpPostRequest(address, infostr, listener);
    }
}

/* TODO
 * 1. 扩展 Info 子类，获取 /proc 文件夹下的其他信息
 * 2. 修改 ProcServer，数据保存与分析
 * 3. 改进 update() 调用时机，调用频率
 */


