package com.example.agc.aigoucai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.agc.aigoucai.R;
import com.example.agc.aigoucai.util.LogUtil;
import com.example.agc.aigoucai.util.SharePreferencesUtil;
import com.example.agc.aigoucai.util.SocketUtil;
import com.example.agc.aigoucai.util.SystemUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;



/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {
    ImageView ivWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        SystemUtil.setfullScreen(SplashActivity.this);
//        ivWelcome=findViewById(R.id.iv_welcome2);

//        String getintent = SharePreferencesUtil.getString(SplashActivity.this, "getIntent", "");
//        if(getintent.contains("com.500CPActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.backgroud_500cp));
//        }
//        if(getintent.contains("com.AigoucaiActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_aigoucai));
//        }
//        if(getintent.contains("com.k7Activity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_k7));
//        }
//        if(getintent.contains("com.ttActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_tt));
//        }
//        if(getintent.contains("com.678yuleActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_678));
//        }
//        if(getintent.contains("com.xpjActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_xpj));
//        }
//        if(getintent.contains("com.zzcActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_zzc));
//        }
//        if(getintent.contains("com.egoActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_egouwangtou));
//        }
//        if(getintent.contains("com.zxcActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_axc));
//        }
//        if(getintent.contains("com.8HaoActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_8hao));
//        }
//        if(getintent.contains("com.pandaActivity")){
//            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.background_panda));
//        }

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
