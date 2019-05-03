package com.example.agc.aigoucai.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.IOException;
import java.net.URL;


public class CommonUtils {

    /**
     * 用java代码的方式动态生成状态选择器
     */
    public static Drawable generatePressedSelector(Drawable pressed, Drawable normal) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//  状态  , 设置按下的图片
        drawable.addState(new int[]{}, normal);//默认状态,默认状态下的图片

        //根据SDK版本设置状态选择器过度动画/渐变选择器/渐变动画
        if (Build.VERSION.SDK_INT > 10) {
            drawable.setEnterFadeDuration(500);
            drawable.setExitFadeDuration(500);
        }

        return drawable;
    }




    public static Drawable loadImageFromNetwork(String imageUrl)
    {
        Drawable drawable = null;
        try {
            // 可以在这里通过文件名来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), "image.jpg");
        } catch (IOException e) {
            Log.e("test", e.getMessage());
        }
        if (drawable == null) {
            Log.e("test", "null drawable");
        } else {
            Log.e("test", "not null drawable");
        }

        return drawable ;
    }



    /**
     * 设置底部tab图标
     * @paramradioButton控件
     * @paramdrawableNormal常态时的图片
     * @paramdrawableSelect选中时的图片
     */
    public static void setSelectorDrawable(RadioButton cbButton, Drawable drawableNormal, Drawable drawableSelect,String text){
                StateListDrawable drawable =new StateListDrawable();
                   //选中
                 drawable.addState(new int[]{android.R.attr.state_checked},drawableSelect);
                 //未选中
                 drawable.addState(new int[]{-android.R.attr.state_checked},drawableNormal);
                 drawable.setBounds(0, 0, 80, 80);
                 cbButton.setCompoundDrawables(null, drawable, null, null);
                 cbButton.setText(text);
    }

}
