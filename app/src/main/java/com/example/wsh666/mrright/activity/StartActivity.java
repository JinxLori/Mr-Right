package com.example.wsh666.mrright.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.util.String_Util;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView background;
    private TextView tv;

    private int recLen = 5;//跳过倒计时提示5秒
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_start);
        initView();

        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);//延迟5S后发送handler信息
    }

    private void initView() {
        /*background = (ImageView) findViewById(R.id.background);*/
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(this);//跳过监听
        /*Glide.with(this).load(R.drawable.background).into(background);*/

        /*从缓存中获取信息*/
        SharedPreferences sp = getSharedPreferences("User", Context.MODE_PRIVATE);
        String username = sp.getString("username",null);
        String password = sp.getString("password",null);
        int userid = sp.getInt("userid",0);
        String headimage = sp.getString("headimage",null);

        String_Util.userId = userid;
        String_Util.userHeadimage = headimage;
        String_Util.username = username;

        /*Log.e("Start",username);*/
        //Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv.setText("跳过 "+recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };
    /**
     * 点击跳过
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}
