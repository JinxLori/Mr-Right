package com.example.wsh666.mrright.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.CommentListAdepter;
import com.example.wsh666.mrright.bean.Comment;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wsh666 on 2018/10/12.
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
public class PostDetailActivity extends Activity implements View.OnClickListener {
    private CircleImageView post_detail_head_image;
    private TextView post_detail_username;
    private ImageView list_close;
    private TextView post_content;
    private TextView post_topic;
    private ImageView post_image;
    private ImageView post_detail_btn_share;
    private TextView share_num;
    private ImageView post_detail_btn_pinglun;
    private TextView pinglun_num;
    private ImageView post_detail_btn_up;
    private TextView up_num;
    private ImageView post_detail_btn_down;
    private ListView comment_list;
    private ImageView fanhui;

    Handler mHandler;
    Post post = new Post();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        setAdapter();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        post_detail_head_image = (CircleImageView) findViewById(R.id.post_detail_head_image);
        post_detail_username = (TextView) findViewById(R.id.post_detail_username);
        list_close = (ImageView) findViewById(R.id.list_close);
        post_content = (TextView) findViewById(R.id.post_content);
        post_topic = (TextView) findViewById(R.id.post_topic);
        post_image = (ImageView) findViewById(R.id.post_image);
        post_detail_btn_share = (ImageView) findViewById(R.id.post_detail_btn_share);
        share_num = (TextView) findViewById(R.id.share_num);
        post_detail_btn_pinglun = (ImageView) findViewById(R.id.post_detail_btn_pinglun);
        pinglun_num = (TextView) findViewById(R.id.pinglun_num);
        post_detail_btn_up = (ImageView) findViewById(R.id.post_detail_btn_up);
        up_num = (TextView) findViewById(R.id.up_num);
        post_detail_btn_down = (ImageView) findViewById(R.id.post_detail_btn_down);
        comment_list = (ListView) findViewById(R.id.comment_list);

        /*获取传递过来的Post详情数据*/
        Bundle bundle = this.getIntent().getExtras();
        post = (Post)bundle.getSerializable("post");

        post_detail_username.setText(post.getUser_name());
        post_content.setText(post.getPost_content_text());
        post_topic.setText(post.getPost_topic());
        pinglun_num.setText(String.valueOf(post.getPost_comment_num()));
        up_num.setText(String.valueOf(post.getPost_nice_num()));


        fanhui.setOnClickListener(this);
        post_detail_head_image.setOnClickListener(this);
        post_detail_username.setOnClickListener(this);
        list_close.setOnClickListener(this);
        post_topic.setOnClickListener(this);
        post_image.setOnClickListener(this);
        post_detail_btn_share.setOnClickListener(this);
        post_detail_btn_pinglun.setOnClickListener(this);
        post_detail_btn_up.setOnClickListener(this);
        post_detail_btn_down.setOnClickListener(this);
    }

    public void setAdapter() {
        final List<Comment> commentList = new ArrayList<>();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Comment comment = new Comment();
                        comment = (Comment) msg.getData().getSerializable("msg");
                        commentList.add(comment);
                        break;
                    default:
                        break;
                }
            }
        };
        CommentListAdepter commentListAdepter = new CommentListAdepter(commentList, PostDetailActivity.this);
        comment_list.setAdapter(commentListAdepter);
        new Thread(){
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetCommentByPostId?post_id="+post.getPost_id()+"&user_id="+String_Util.userId);
                Gson gson = new Gson();
                List<Comment> comments = gson.fromJson(jsonData,
                        new TypeToken<List<Comment>>() {}.getType());
                for(Comment comment : comments) {
                    Log.e("123" , comment.toString());
                    Message message=new Message();
                    message.what=1;//判断是哪个handler的请求
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("msg",comment);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:

                break;
            case R.id.post_detail_head_image:

                break;
            case R.id.post_detail_username:

                break;
            case R.id.list_close:

                break;
            case R.id.post_topic:

                break;
            case R.id.post_image:

                break;
            case R.id.post_detail_btn_share:

                break;
            case R.id.post_detail_btn_pinglun:

                break;
            case R.id.post_detail_btn_up:

                break;
            case R.id.post_detail_btn_down:

                break;
        }
    }
}