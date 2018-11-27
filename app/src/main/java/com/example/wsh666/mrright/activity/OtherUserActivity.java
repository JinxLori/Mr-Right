package com.example.wsh666.mrright.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.PostListAdapter;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserActivity extends AppCompatActivity implements View.OnClickListener {

    Post post = new Post();
    private Toolbar toolbar;
    private CircleImageView personal_head_image;
    private ImageView image;
    private CollapsingToolbarLayout collapsing_toolbar;
    private AppBarLayout appBar;
    private FloatingActionButton float_button;

    List<Post> postList;
    ListView listView ;
    Handler mHandler;
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
        setContentView(R.layout.activity_other_user);


        /*获取传递过来的Post详情数据*/
        Bundle bundle = this.getIntent().getExtras();
        post = (Post) bundle.getSerializable("post");

        initView();

        selectPost();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        personal_head_image = (CircleImageView) findViewById(R.id.personal_head_image);
        image = (ImageView) findViewById(R.id.image);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) findViewById(R.id.appBar);
        float_button = (FloatingActionButton) findViewById(R.id.float_button);
        listView = (ListView) findViewById(R.id.postList);

        collapsing_toolbar.setTitle(post.getUsername());
        Glide.with(this).load(post.getHeadimage()).into(image);
        Glide.with(this).load(post.getHeadimage()).into(personal_head_image);

        personal_head_image.setOnClickListener(this);
        float_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_head_image:

                break;
            case R.id.float_button:
                Bundle b = new Bundle();
                b.putStringArray("chat_send_information",new String[]{String.valueOf(post.getPost_from_id()),post.getUsername()});
                Intent intent  = new Intent();
                intent.setClass(this, Chatctivity.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }

    public void selectPost(){
        postList = new ArrayList<>();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Post post = new Post();
                        post = (Post) msg.getData().getSerializable("msg");
                        postList.add(post);
                        postListAdapter = new PostListAdapter(postList,OtherUserActivity.this);
                        listView.setAdapter(postListAdapter);
                        break;
                    default:
                        break;
                }
            }
        };
//        PostListAdapter postListAdapter = new PostListAdapter(postList,getActivity());
//        listView.setAdapter(postListAdapter);

        /*查询帖子的线程*/
        new Thread(){
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetPostByUserIdServlet?user_id="+ String_Util.userId);
                Gson gson = new Gson();
                List<Post> posts = gson.fromJson(jsonData,
                        new TypeToken<List<Post>>() {}.getType());
                if(posts==null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OtherUserActivity.this, "请求数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    for(Post post : posts) {
                        Log.e("Post" , post.toString());
                        Message message=new Message();
                        message.what=1;//判断是哪个handler的请求
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("msg",post);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }
            }
        }.start();
    }
}
