package com.example.wsh666.mrright.util;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsh666 on 2018/9/13.
 * 　 へ　　　　　  ／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　   /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／
 * 　 / へ　　 /　ﾉ＜| ＼＼
 * 　 ヽ_ﾉ　　(_／　 │／／
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 */

/*
* 轮播图的图片与链接传入
* */
public class ImageAndAddress {
    private ArrayList<ImageView> imageViews;
    private  List<String>https;

    public ImageAndAddress() {

    }
    /*单例模式*/
    private static ImageAndAddress single = null;
    public static ImageAndAddress getInstance() {
        if(single == null){
            single = new ImageAndAddress();
        }
        return single;
    }

    public ImageAndAddress(ArrayList<ImageView> imageViews, List<String> https) {
        this.imageViews = imageViews;
        this.https = https;
    }

    public List<String> getHttps() {
        return https;
    }

    public void setHttps(List<String> https) {
        this.https = https;
    }

    public ArrayList<ImageView> getImageViews() {
        return imageViews;
    }

    public void setImageViews(ArrayList<ImageView> imageViews) {
        this.imageViews = imageViews;
    }
}
