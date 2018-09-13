package com.example.wsh666.mrright.my_view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.util.ImageAndAddress;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wsh666 on 2018/9/11.
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

public class My_SlideShow extends FrameLayout {
    //自动轮播启用开关
    public static final boolean isAutoPlay = true;
    private Context mContext;
    ArrayList<String> mList;
    ArrayList<ImageView> mImageViews;
    ArrayList<View> mViews;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService mScheduledExecutorService;
    ViewPager mViewPager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(currentItem);
        }
    };

    public My_SlideShow(@NonNull Context context) {
        this(context, null);
    }

    public My_SlideShow(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public My_SlideShow(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //初始化数据
        initData();
    }

    private void initData() {

        mImageViews = new ArrayList<>();
        mViews = new ArrayList<>();
        //初始化布局
        initUI(mContext);
        //初始化定时任务的方法
        initTimer();
    }

    private void initTimer() {
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 3, TimeUnit.SECONDS);
        /*new SlideShowTask()是一个实现Runnable接口的类，会自动运行里面的run()方法，1的意思就是启动等待时间，这里就是直接运行，
        3是3秒，要是想小时，就把TimeUnit.SECONDS TimeUnit.HOURS，分钟是TimeUnit.MINUTES*/
    }

    //执行轮播图切换任务
    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            currentItem = (currentItem + 1) % mImageViews.size();
            mHandler.obtainMessage().sendToTarget();
        }
    }

    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.slidsow, this, true);
        LinearLayout douLayout = (LinearLayout) findViewById(R.id.linearlatouy_slidsow);
        douLayout.removeAllViews();
        /*//显示图片
        ImageView view1 = new ImageView(mContext);
        view1.setBackgroundResource(R.drawable.test);
        ImageView view2 = new ImageView(mContext);
        view2.setBackgroundResource(R.drawable.add);
        ImageView view3 = new ImageView(mContext);
        view3.setBackgroundResource(R.drawable.test);
        ImageView view4 = new ImageView(mContext);
        view4.setBackgroundResource(R.drawable.my_lishi);
        mImageViews.add(view1);
        mImageViews.add(view2);
        mImageViews.add(view3);
        mImageViews.add(view4);*/
        /*得到图片，这里没有数据，但是在加载此页面之前应该调用setImageViews()方法来填充数据*/
        ImageAndAddress imageAndAddress=ImageAndAddress.getInstance();
        mImageViews=imageAndAddress.getImageViews();
        //小点
        for (int i = 0; i < mImageViews.size(); i++) {
            //初始化轮播图的点击监听事件
            mImageViews.get(i).setId(i);
            mImageViews.get(i).setOnClickListener(new MyListener());
            View pointView = new View(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            //初始化圆点以及颜色
            if (i == 0) {
                pointView.setBackgroundResource(R.color.colorAccent);
            } else {
                pointView.setBackgroundResource(R.color.colorPrimary);
            }
            params.leftMargin = 10;
            pointView.setLayoutParams(params);
            mViews.add(pointView);
            //将小点添加到布局中去
            douLayout.addView(pointView, params);
        }
        mViewPager = (ViewPager) findViewById(R.id.viewpager_slidsow);
        //启动适配器
        MyPagerAdaapter adaapter = new MyPagerAdaapter();
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(adaapter);
        //初始化轮播图的滑动监听事件
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        //设置初始化需要显示的条目数
        mViewPager.setCurrentItem(0);
    }

    //自定义轮播图点击事件
    class MyListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case 0:
                    Toast.makeText(mContext, "点击的是第一张轮播图！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(mContext, "点击的是第二张轮播图！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "点击的是第三张轮播图！", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(mContext, "点击的是第四张轮播图！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //自定义PageChangeListener
    class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        //滑动被调用
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //滑动状态被选定
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            for (int i = 0; i < mViews.size(); i++) {
                if (i == position) {
                    mViews.get(i).setBackgroundResource(R.color.colorAccent);
                } else {
                    mViews.get(i).setBackgroundResource(R.color.colorPrimary);
                }
            }
        }

        //滑动状态改变时
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    //自定义PagerAdapter
    class MyPagerAdaapter extends PagerAdapter {
        //返回轮播图的最大显示条目数
        @Override
        public int getCount() {
            return mImageViews.size();
        }

        //判断View是否可复用
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //返回要显示的条目，并创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition = position % mImageViews.size();
            ImageView imageView = mImageViews.get(newPosition);
            container.addView(imageView);
            return imageView;
        }

        //销毁看不到的条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }
    }
}
