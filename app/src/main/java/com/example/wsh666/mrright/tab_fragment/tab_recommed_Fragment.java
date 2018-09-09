package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;

import java.util.ArrayList;

/**
 * Created bywsh666 on 2018/9/8 13:49
 　 へ　　　　　  ／|
　　/＼7　　　 ∠＿/
　 /　│　　 ／　／
　│　Z ＿,＜　／　　 /`ヽ
　│　　　　　ヽ　　 /　　〉
　 Y　　　　　`　   /　　/
　ｲ●　､　●　　⊂⊃〈　　/
　()　 へ　　　　|　＼〈
　　>ｰ ､_　 ィ　 │ ／／
　 / へ　　 /　ﾉ＜| ＼＼
　 ヽ_ﾉ　　(_／　 │／／
　　7　　　　　　　|／
　　＞―r￣￣`ｰ―＿
 */
public class tab_recommed_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener{


    private ViewPager vpager_four;
    private ImageView img_cursor;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离

    public tab_recommed_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab_recommed_, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        vpager_four = (ViewPager) view.findViewById(R.id.vpager_four);
        vpager_four.setOffscreenPageLimit(6);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one * 2;// 移动两页的偏移量,比如1直接跳3


        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.view_one, null));
        listViews.add(mInflater.inflate(R.layout.view_two, null));
        listViews.add(mInflater.inflate(R.layout.view_three, null));

        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);//设置ViewPager当前页，从0开始算
        tv_one.setTextColor(getResources().getColor(R.color.colorAccent));

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);

        vpager_four.addOnPageChangeListener(this);

        View view_one=mInflater.inflate(R.layout.view_one,null);
        Button button1=(Button) view_one.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("asd","a");
            }
        });
        View view_two=LayoutInflater.from(getActivity()).inflate(R.layout.view_two,null);
        Button button2=view_two.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("asd","b");
            }
        });
        View view_three=LayoutInflater.from(getActivity()).inflate(R.layout.view_three,null);
        Button button3=view_three.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("asd","c");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                vpager_four.setCurrentItem(0);
                break;
            case R.id.tv_two:
                vpager_four.setCurrentItem(1);
                break;
            case R.id.tv_three:
                vpager_four.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageSelected(int index) {
        chageTextColor();
        Animation animation = null;
        switch (index) {
            case 0:
                tv_one.setTextColor(getResources().getColor(R.color.colorAccent));
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                tv_two.setTextColor(getResources().getColor(R.color.colorAccent));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                tv_three.setTextColor(getResources().getColor(R.color.colorAccent));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /*更改顶部选项字体颜色*/
    public void chageTextColor(){
        tv_one.setTextColor(getResources().getColor(R.color.black));
        tv_two.setTextColor(getResources().getColor(R.color.black));
        tv_three.setTextColor(getResources().getColor(R.color.black));
    }

}
