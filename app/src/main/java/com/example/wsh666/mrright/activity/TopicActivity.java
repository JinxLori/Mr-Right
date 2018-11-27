package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.TopicListAdapter;
import com.example.wsh666.mrright.bean.Topic;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private ListView topic_list;
    private Handler mHandler;
    TopicListAdapter topicListAdapter;
    List<Topic> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*沉浸式状态栏，不需要设置主题啥的了*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_topic);
        initView();
        topicList = new ArrayList<>();
        GetTopicThread getTopicThread = new GetTopicThread();
        getTopicThread.start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Topic topic = new Topic();
                        topic = (Topic) msg.getData().getSerializable("msg");
                        topicList.add(topic);

                        topicListAdapter = new TopicListAdapter(topicList, TopicActivity.this);
                        topic_list.setAdapter(topicListAdapter);
                        Log.e("gettopic", topic.toString());
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        topic_list = (ListView) findViewById(R.id.topic_list);
        fanhui.setOnClickListener(this);

        topic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic",topicList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(TopicActivity.this, TopicPostActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    /*得到所有话题的线程*/
    public class GetTopicThread extends Thread{
        @Override
        public void run() {
            Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
            String jsonData = get_data_fromWeb.getData(String_Util.urlString + "GetTopic");
            Gson gson = new Gson();
            List<Topic> topics = gson.fromJson(jsonData,
                    new TypeToken<List<Topic>>() {
                    }.getType());
            if (topics == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TopicActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                for (Topic topic : topics) {
                    Log.e("topic", topic.toString());
                    Message message = new Message();
                    message.what = 1;//判断是哪个handler的请求
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("msg", topic);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        }
    }
}
