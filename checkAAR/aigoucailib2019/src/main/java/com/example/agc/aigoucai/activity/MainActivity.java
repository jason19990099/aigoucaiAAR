package com.example.agc.aigoucai.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.agc.aigoucai.R;
import com.example.agc.aigoucai.util.LogUtil;

public class MainActivity extends AppCompatActivity {
    private WebView webview ;
    // h5 地址
    private String reurl = "";

    // 用来显示视频的布局
    private FrameLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mLayout =  findViewById(R.id.fl_video);
        webview = findViewById(R.id.webview);

        Bundle bundle = this.getIntent().getExtras();
        if (null != bundle)
            reurl = bundle.getString("url");
        LogUtil.e("======reurl======="+reurl);

        initWebView();
    }

    /**
     * 设置webView 相关属性
     */

    private void initWebView() {
        WebSettings setting= webview.getSettings();
        setting.setJavaScriptEnabled(true);// 设置支持javascript脚本
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存模式
        webview.setVerticalScrollBarEnabled(false); // 取消Vertical ScrollBar显示
        webview.setHorizontalScrollBarEnabled(false); // 取消Horizontal ScrollBar显示
        //设置自适应屏幕，两者合用
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);

        setting.setAllowFileAccess(true);// 允许访问文件
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webview.setFocusable(false); // 去掉超链接的外边框
//        setting.setDefaultTextEncodingName("GBK");//设置文本编码（根据页面要求设置）
        setting.setMediaPlaybackRequiresUserGesture(false);
        mLayout.setVisibility(View.VISIBLE);
        webview.setVisibility(View.VISIBLE);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.loadUrl(reurl);
    }

    private class MyWebChromeClient extends WebChromeClient {
        private CustomViewCallback mCustomViewCallback;
        //  横屏时，显示视频的view
        private View mCustomView;
        // 点击全屏按钮时，调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);

            //如果view 已经存在，则隐藏
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomView = view;
            mCustomView.setVisibility(View.VISIBLE);
            mCustomViewCallback = callback;
            mLayout.addView(mCustomView);
            mLayout.setVisibility(View.VISIBLE);
            mLayout.bringToFront();
            LogUtil.e("==============setRequestedOrientation=============");
            //设置横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // 取消全屏调用的方法
        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mLayout.removeView(mCustomView);
            mCustomView = null;
            mLayout.setVisibility(View.GONE);
            try {
                mCustomViewCallback.onCustomViewHidden();
            } catch (Exception e) {
            }
//            titleView.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }


    }
    /**
     * 横竖屏切换监听
     */
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.destroy();
        webview = null;
    }


}
