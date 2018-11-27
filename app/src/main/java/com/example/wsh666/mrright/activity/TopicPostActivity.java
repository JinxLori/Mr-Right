package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.PostListAdapter;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.bean.Topic;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TopicPostActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private TextView topic_title;
    private ListView topicPostList;
    private ImageView topic_image;

    Topic topic = new Topic();

    private Handler mHandler;
    List<Post> postList;
    PostListAdapter postListAdapter;

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
        setContentView(R.layout.activity_topic_post);

        /*接受列表传递过来的topic*/
        Bundle bundle = this.getIntent().getExtras();
        topic = (Topic) bundle.getSerializable("topic");

        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        topic_title = (TextView) findViewById(R.id.topic_title);
        topicPostList = (ListView) findViewById(R.id.topicPostList);
        topic_image = (ImageView) findViewById(R.id.topicimage);

        topic_title.setText(topic.getTopic_content());
        Glide.with(this).load(topic.getTopic_image()).into(topic_image);
        fanhui.setOnClickListener(this);

        /*根据TopicID查询帖子*/
        GetPostByTopicThread getPostByTopicThread = new GetPostByTopicThread();
        getPostByTopicThread.start();
        postList = new ArrayList<>();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Post post = new Post();
                        post = (Post) msg.getData().getSerializable("msg");
                        postList.add(post);

                        postListAdapter = new PostListAdapter(postList,TopicPostActivity.this);
                        topicPostList.setAdapter(postListAdapter);
                        Log.e("getpostByTopic", topic.toString());
                        break;
                    default:
                        break;
                }
            }
        };
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

    public class GetPostByTopicThread extends Thread{
        @Override
        public void run() {
            Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
            String jsonData = get_data_fromWeb.getData(String_Util.urlString + "GetPostByTopicId?user_id="+String_Util.userId+"&topic_id="+topic.getTopic_id());
            Gson gson = new Gson();
            List<Post> posts = gson.fromJson(jsonData,
                    new TypeToken<List<Post>>() {
                    }.getType());
            if (posts == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TopicPostActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                for (Post post : posts) {
                    Log.e("postByTopic", post.toString());
                    Message message = new Message();
                    message.what = 1;//判断是哪个handler的请求
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("msg", post);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        }
    }
}
