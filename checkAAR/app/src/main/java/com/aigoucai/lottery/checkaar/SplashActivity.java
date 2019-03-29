package com.aigoucai.lottery.checkaar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.agc.aigoucai.activity.MainWebviewNormalActivity2;
import com.example.agc.aigoucai.bean.Aardata;
import com.example.agc.aigoucai.bean.Basedata;
import com.example.agc.aigoucai.util.LogUtil;
import com.example.agc.aigoucai.util.SharePreferencesUtil;
import com.example.agc.aigoucai.util.SystemUtil;
import com.example.agc.aigoucai.util.TrustAllCerts;
import com.example.agc.aigoucai.util.changIcoinUtils;
import com.google.gson.Gson;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashActivity extends AppCompatActivity {
    private List<Appdata.Appdatas> datas =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemUtil.setfullScreen(SplashActivity.this);
        getChatdata();
    }

    private void getChatdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(9, TimeUnit.SECONDS)
                            .writeTimeout(9, TimeUnit.SECONDS)
                            .sslSocketFactory(createSSLSocketFactory())
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .build();
                    String url = "http://hk1.android.jrapp.me/switch/xhy";
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    final String s = response.body().string();
                    LogUtil.e("=========s======"+s);
                    if (response.isSuccessful()) {
                        Aardata aardata=new Gson().fromJson(s,Aardata.class);
                        //总开关开
                        if (aardata.getCode()==1){
                            //显示图标的key
                            String extend_1_title=aardata.getData().getExtend_1_title();
                            String[] extend_1_titleArray = extend_1_title.split(",");
                            if (extend_1_titleArray.length==0&&extend_1_title.length()>0){
                                extend_1_titleArray= new String[]{extend_1_title};
                            }
                            //请求服务器的appid
                            String extend_1_url=aardata.getData().getExtend_1_url();
                            String[] extend_1_urlArray = extend_1_url.split(",");
                            if (extend_1_urlArray.length==0&&extend_1_url.length()>0){
                                extend_1_urlArray= new String[]{extend_1_url};
                            }
                            List list_key=new ArrayList();

                            //显示桌面图标
                            if (extend_1_titleArray.length==extend_1_urlArray.length){
                                for (int m=0;m<extend_1_titleArray.length;m++){
                                    Appdata.Appdatas  appdatas=new Appdata.Appdatas();
                                    appdatas.setExtend_1_title(extend_1_titleArray[m]);
                                    appdatas.setExtend_1_url(extend_1_urlArray[m]);
                                    datas.add(appdatas);
                                    list_key.add(extend_1_titleArray[m]);
                                }
                            }
                            changIcoinUtils.addmore(SplashActivity.this,list_key);


                            ComponentName componentName = SplashActivity.this.getComponentName();
                            PackageManager pm = getPackageManager();
                            ActivityInfo activityInfo = null;
                            try {
                                activityInfo = pm.getActivityInfo (componentName, 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }

                            Log.e ("ActivityLabel=====", activityInfo.loadLabel (pm).toString ());
                            Log.e("getIntent()======",getIntent().getComponent().getClassName());
                            SharePreferencesUtil.addString(SplashActivity.this,"getIntent",getIntent().getComponent().getClassName());
                            for (int i = 0; i< datas.size(); i++){
                                if (getIntent().getComponent().getClassName().contains(datas.get(i).getExtend_1_title())){
                                    Basedata.appid= datas.get(i).getExtend_1_url();
                                }
                            }
                            LogUtil.e("=========Basedata.appid====="+Basedata.appid);

                            if (!getIntent().getComponent().getClassName().equals("com.aigoucai.lottery.checkaar.SplashActivity")){
                                //解析地址
                                String app_url=aardata.getData().getApp_url();
                                String[] sourceStrArray = app_url.split(",");
                                List list=new ArrayList();
                                for (int i = 0; i < sourceStrArray.length; i++) {
                                    list.add(sourceStrArray[i]);
                                }
                                Intent intent=new Intent(SplashActivity.this, com.example.agc.aigoucai.activity.SplashActivity.class);
                                intent.putStringArrayListExtra("ip_array", (ArrayList<String>) list);
                                startActivity(intent);
                                finish();
                            }else{
                                //做马甲包的跳转
                                Intent intent=new Intent(SplashActivity.this, MainWebviewNormalActivity2.class);
                                intent.putExtra("url","http://15930.vip/");
                                startActivity(intent);
                            }

                        }else{
                            //做马甲包的跳转
                            Intent intent=new Intent(SplashActivity.this, MainWebviewNormalActivity2.class);
                            intent.putExtra("url","http://15930.vip/");
                            startActivity(intent);

                        }

                    }else{
                        //做马甲包的跳转
                        Intent intent=new Intent(SplashActivity.this, MainWebviewNormalActivity2.class);
                        intent.putExtra("url","http://15930.vip/");
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("==========e======"+e.toString());

                }
            }
        }).start();
    }



    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }


    @Override
    protected void onStop() {
        super.onStop();
        //这里要加 控制生命周期
        finish();
    }


}
