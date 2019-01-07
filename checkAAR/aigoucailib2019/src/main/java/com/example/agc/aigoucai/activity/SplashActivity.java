package com.example.agc.aigoucai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.agc.aigoucai.R;
import com.example.agc.aigoucai.util.LogUtil;
import com.example.agc.aigoucai.util.SocketUtil;
import com.example.agc.aigoucai.util.SystemUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;



/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SystemUtil.setfullScreen(SplashActivity.this);

        List<String> ip_array = (List<String>) getIntent().getSerializableExtra("ip_array");

        //ip和端口号传进去
        SocketUtil socketUtil = new SocketUtil(ip_array, 1985, SplashActivity.this);
        //调取方法开始连接
        socketUtil.getSocketConection();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SelectLinesActivity.class));
                finish();
            }
        }, 1500);

    }


    /**
     * 解析域名获取IP数组
     *
     * @param host
     * @return
     */
    public String[] parseHostGetIPAddress(String host) {
        String[] ipAddressArr = null;
        try {
            InetAddress[] inetAddressArr = InetAddress.getAllByName(host);
            if (inetAddressArr != null && inetAddressArr.length > 0) {
                ipAddressArr = new String[inetAddressArr.length];
                for (int i = 0; i < inetAddressArr.length; i++) {
                    ipAddressArr[i] = inetAddressArr[i].getHostAddress();
                }
            }
        } catch (UnknownHostException e) {
            LogUtil.e("========123=======" + e.toString());
            e.printStackTrace();
            return null;
        }
        return ipAddressArr;
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


}
