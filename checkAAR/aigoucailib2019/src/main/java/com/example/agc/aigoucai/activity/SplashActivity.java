package com.example.agc.aigoucai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.agc.aigoucai.R;
import com.example.agc.aigoucai.bean.Basedata;
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
        setContentView(R.layout.activity_splashs);
        ivWelcome=findViewById(R.id.iv_welcome);

        String  getintent=SharePreferencesUtil.getString(SplashActivity.this,"getIntent","");
        if(getintent.contains("com.500CPActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_500));
            Basedata.share_url="https://www.500app.me/app.html";
        }
        if(getintent.contains("com.AigoucaiActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_agc));
            Basedata.share_url="https://www.agcapp.me/app.html";
        }
        if(getintent.contains("com.k7Activity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_k7));
            Basedata.share_url="https://www.k7app.me/app.html";
        }
        if(getintent.contains("com.ttActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_tt));
            Basedata.share_url="https://www.ttapp.me/app.html";
        }
        if(getintent.contains("com.678yuleActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_678));
            Basedata.share_url="https://www.appkings.me/678.html";
        }
        if(getintent.contains("com.xpjActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_xpj));
            Basedata.share_url="https://www.appkings.me/xpj.html";
        }
        if(getintent.contains("com.zzcActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_zzc));
            Basedata.share_url="https://www.appkings.me/zzc.html";
        }
        if(getintent.contains("com.egoActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_egou));
            Basedata.share_url="https://www.appkings.me/eg.html";
        }
        if(getintent.contains("com.zxcActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_zxc));
            Basedata.share_url="https://www.appkings.me/zxc.html";
        }
        if(getintent.contains("com.8HaoActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_8hao));
            Basedata.share_url="https://www.appkings.me/8hao.html";
        }
        if(getintent.contains("com.pandaActivity")){
            ivWelcome.setImageDrawable(getResources().getDrawable(R.mipmap.welcome_panda));
            Basedata.share_url="https://www.agcapp.me/xm.html";
        }

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
