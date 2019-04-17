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
import com.example.agc.aigoucai.activity.MainWebviewPandaActivity2;
import com.example.agc.aigoucai.bean.Aardata;
import com.example.agc.aigoucai.bean.Basedata;
import com.example.agc.aigoucai.util.LogUtil;
import com.example.agc.aigoucai.util.SharePreferencesUtil;
import com.example.agc.aigoucai.util.SystemUtil;
import com.example.agc.aigoucai.util.TrustAllCerts;
import com.example.agc.aigoucai.util.changIcoinUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashActivity extends AppCompatActivity {
    private List<Appdata.Appdatas> datas =new ArrayList<>();
     List<String> list= new ArrayList<>();
    private IParray  iParray;

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
                                goMajiabao();
                            }

                        }else{
                            //做马甲包的跳转
                            goMajiabao();

                        }

                    }else{
                        goMajiabao();
                    }
                } catch (Exception e) {
                    goMajiabao();
                    e.printStackTrace();
                    LogUtil.e("==========e======"+e.toString());

                }
            }
        }).start();
    }

    private void goMajiabao() {
        String url = "http://api.dd7666.com/index.php?r=index/getapi_ya";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("123", "onFailure: ");
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String s=response.body().string();
                iParray=new Gson().fromJson(s,IParray.class);

                for (int i=0;i<iParray.getData().size();i++){
                    list.add(iParray.getData().get(i));
                }
                String url="";
                for (int m=0;m<list.size();m++){
                    if (!list.get(m).contains("http://")&&!list.get(m).contains("https://")){
                        url="http://"+list.get(m);
                    }
                    sendHttpRequest(url,m);
                }
            }


        });

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




    public SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
    //请求网址响应
    public void sendHttpRequest(final String address, final int i) {
        if (null==address){
            return ;
        }
        new Thread(new Runnable() {
            long between = 0;
            String date2 = "";
            public void run() {

                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(9999);
                    connection.setReadTimeout(9999);
                    final String date1 = dfs.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        date2 = dfs.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                        Date begin = dfs.parse(date1);
                        Date end = dfs.parse(date2);
                        between = Math.abs((end.getTime() - begin.getTime()));// 得到两者的毫秒数
                        Log.e("between",between+"");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //做马甲包的跳转
                                Intent intent = new Intent(SplashActivity.this, MainWebviewPandaActivity2.class);
                                intent.putExtra("url", address);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Log.e("between","失敗了。。。。。。");
                        //做马甲包的跳转
                    }

                } catch (Exception e) {
                    Log.e("between","發生錯誤了。。。。。。"+e.toString());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
        }).start();


    }


}
