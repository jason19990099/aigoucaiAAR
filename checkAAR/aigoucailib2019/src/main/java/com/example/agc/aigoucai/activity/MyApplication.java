package com.example.agc.aigoucai.activity;

import android.app.Application;

import com.example.agc.aigoucai.util.Apputil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuhao.android.libsocket.sdk.OkSocket;


/**
 * Created by Administrator on 2018/3/26 0026.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //去掉证书验证的问题/
        Apputil.handleSSLHandshake();
//        OkSocket.initialize(this);
        //如果需要开启Socket调试日志,请配置
        OkSocket.initialize(this, true);
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(getApplicationContext(), "5ca46c9661f564da46000a12", "android-xiaohuangya", UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }


}
