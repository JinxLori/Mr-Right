package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.LoginActivity;
import com.example.wsh666.mrright.activity.MainActivity;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;
import com.example.wsh666.mrright.fragment.Edit_Information_Fragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created bywsh666 on 2018/9/10 13:48
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
public class Tab_Personal_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener{


    private ImageView image;
    private CollapsingToolbarLayout collapsing_toolbar;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private FloatingActionButton float_button;
    private CircleImageView personal_head_image;
    private Fragment editInformationFragment;
    private TextView huozan_num;
    private LinearLayout huozan;
    private TextView guanzhu_num;
    private LinearLayout guanzhu;
    private TextView fensi_num;
    private LinearLayout fensi;
    private TextView tiezi;
    private TextView genpai;
    private TextView pinglun;
    private TextView huati;
    private ImageView img_cursor;
    private ViewPager vpager_personal;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int bmpHeight;
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离
    private int three=0;

    public Tab_Personal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_personal_, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) view.findViewById(R.id.appBar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        collapsing_toolbar.setTitle("Test");
        Glide.with(this).load(R.drawable.test).into(image);

        /*头像*/
        personal_head_image = (CircleImageView) view.findViewById(R.id.personal_head_image);
        personal_head_image.setImageResource(R.drawable.test);
        personal_head_image.setOnClickListener(this);
        /*悬浮按钮点击*/
        float_button = (FloatingActionButton) view.findViewById(R.id.float_button);
        float_button.setOnClickListener(this);

        huozan_num = (TextView) view.findViewById(R.id.huozan_num);
        huozan_num.setText("6");
        huozan = (LinearLayout) view.findViewById(R.id.huozan);
        huozan.setOnClickListener(this);
        guanzhu_num = (TextView) view.findViewById(R.id.guanzhu_num);
        guanzhu_num.setText("2");
        guanzhu = (LinearLayout) view.findViewById(R.id.guanzhu);
        guanzhu.setOnClickListener(this);
        fensi_num = (TextView) view.findViewById(R.id.fensi_num);
        fensi_num.setText("3");
        fensi = (LinearLayout) view.findViewById(R.id.fensi);
        fensi.setOnClickListener(this);


        tiezi = (TextView) view.findViewById(R.id.tiezi);
        tiezi.setOnClickListener(this);
        genpai = (TextView) view.findViewById(R.id.genpai);
        genpai.setOnClickListener(this);
        pinglun = (TextView) view.findViewById(R.id.pinglun);
        pinglun.setOnClickListener(this);
        huati = (TextView) view.findViewById(R.id.huati);
        huati.setOnClickListener(this);
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);
        img_cursor.setOnClickListener(this);
        vpager_personal = (ViewPager) view.findViewById(R.id.vpager_personal);
        vpager_personal.setOnClickListener(this);


        //下划线动画的相关设置：
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.line);
        bmpWidth = bitmap.getWidth();// 获取图片宽度
        bmpHeight = bitmap.getHeight();
        Log.e("1",String.valueOf(bmpWidth));
        int width=50;
        int hight=8;
        float scaleWidth=((float)width)/bmpWidth;
        float scaleHeight=((float)hight)/bmpHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        bitmap=Bitmap.createBitmap(bitmap,0,0,bmpWidth,bmpHeight,matrix,true);
        img_cursor.setImageBitmap(bitmap);
        bmpWidth=bitmap.getWidth();
        Log.e("2",String.valueOf(bmpWidth));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        Log.e("screen",String.valueOf(screenW));
        offset = (screenW / 4 - bmpWidth) / 2;// 计算偏移量
        Log.e("offset",String.valueOf(offset));
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one* 2 ;
        three=one*3;

        /*初始化ViewPager的几个页面*/
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        View view_one=mInflater.inflate(R.layout.view_one,null);
        View view_two=mInflater.inflate(R.layout.view_two,null);
        View view_three=mInflater.inflate(R.layout.view_three,null);
        View view_four=mInflater.inflate(R.layout.view_one,null);


        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        /*LayoutInflater mInflater = getActivity().getLayoutInflater();*/
        listViews.add(view_one);
        listViews.add(view_two);
        listViews.add(view_three);
        listViews.add(view_four);

        vpager_personal.setAdapter(new MyPagerAdapter(listViews));
        vpager_personal.setCurrentItem(0);//设置ViewPager当前页，从0开始算
        tiezi.setTextColor(getResources().getColor(R.color.green));

        vpager_personal.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                /*Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();*/
                editInformationFragment = new Edit_Information_Fragment();
                ((MainActivity) getActivity()).replaceFragment(editInformationFragment, this);
                break;
            case R.id.personal_head_image:
                //Toast.makeText(getActivity(), "头像", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.huozan:

                break;
            case R.id.guanzhu:

                break;
            case R.id.fensi:

                break;
            case R.id.tiezi:
                vpager_personal.setCurrentItem(0);
                break;
            case R.id.genpai:
                vpager_personal.setCurrentItem(1);
                break;
            case R.id.pinglun:
                vpager_personal.setCurrentItem(2);
                break;
            case R.id.huati:
                vpager_personal.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int index) {

        chageTextColor();
        Animation animation = null;
        switch (index) {
            case 0:
                tiezi.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, 0, 0, 0);
                }
                break;
            case 1:
                genpai.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, one, 0, 0);
                }
                break;
            case 2:
                pinglun.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                } else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, two, 0, 0);
                }
                break;
            case 3:
                huati.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, three, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, three, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, three, 0, 0);
                }
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*更改顶部选项字体颜色*/
    public void chageTextColor(){
        tiezi.setTextColor(getResources().getColor(R.color.black));
        genpai.setTextColor(getResources().getColor(R.color.black));
        pinglun.setTextColor(getResources().getColor(R.color.black));
        huati.setTextColor(getResources().getColor(R.color.black));
    }
}
