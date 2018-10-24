package com.example.wsh666.mrright.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.CommentListAdepter;
import com.example.wsh666.mrright.bean.Comment;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.SoftInputUtil;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
    private EditText comment_edit;
    private ImageView send_comment;
    private ImageView fanhui;

    Handler mHandler;
    Post post = new Post();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initView();
        //setAdapter();
    }

    /*重写方法实现返回值后数据实时刷新*/
    @Override
    protected void onResume() {
        super.onResume();
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
        post = (Post) bundle.getSerializable("post");

        post_detail_username.setText(post.getUser_name());
        post_content.setText(post.getPost_content_text());
        post_topic.setText(post.getPost_topic());
        pinglun_num.setText(String.valueOf(post.getPost_comment_num()));
        up_num.setText(String.valueOf(post.getPost_nice_num()));
        if (post.getIs_nice().equals("true")) {
            post_detail_btn_up.setImageResource(R.drawable.uped);
        }


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
        comment_edit = (EditText) findViewById(R.id.comment_edit);
        comment_edit.setOnClickListener(this);
        send_comment = (ImageView) findViewById(R.id.send_comment);
        send_comment.setOnClickListener(this);
    }

    public void setAdapter() {
        final List<Comment> commentList = new ArrayList<>();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Comment comment = new Comment();
                        comment = (Comment) msg.getData().getSerializable("msg");
                        commentList.add(comment);
                        /*放在Hander里面，数据加载完成之后才会加载视图，不然会出现数据加载慢了之后视图得不到数据从而不显示*/
                        CommentListAdepter commentListAdepter = new CommentListAdepter(commentList, PostDetailActivity.this);
                        comment_list.setAdapter(commentListAdepter);
                        break;
                    default:
                        break;
                }
            }
        };
        /*CommentListAdepter commentListAdepter = new CommentListAdepter(commentList, PostDetailActivity.this);
        comment_list.setAdapter(commentListAdepter);*/
        new Thread() {
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString + "GetCommentByPostId?post_id=" + post.getPost_id() + "&user_id=" + String_Util.userId);
                Gson gson = new Gson();
                List<Comment> comments = gson.fromJson(jsonData,
                        new TypeToken<List<Comment>>() {
                        }.getType());
                for (Comment comment : comments) {
                    Log.e("123", comment.toString());
                    Message message = new Message();
                    message.what = 1;//判断是哪个handler的请求
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("msg", comment);
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
                //已点赞（不可点赞）
                if (post_detail_btn_up.getDrawable().getCurrent().getConstantState().equals(PostDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())) {
                    Toast.makeText(PostDetailActivity.this, "不可多次点赞", Toast.LENGTH_SHORT).show();
                } else {//未点赞（可点赞）进入点赞的子线程
                    AddNiceNumThread addThread = new AddNiceNumThread();
                    addThread.start();
                }
                break;
            case R.id.post_detail_btn_down:
                //已点赞(取消赞)
                if (post_detail_btn_up.getDrawable().getCurrent().getConstantState().equals(PostDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())) {
                    CancelNiceNumThread cancelThread = new CancelNiceNumThread();
                    cancelThread.start();
                } else {//未点赞，不进行操作，进入取消点赞的子线程
                    Toast.makeText(PostDetailActivity.this, "没有可取消的赞", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.comment_edit:
                SoftInputUtil.showSoftInputFromWindow(PostDetailActivity.this,comment_edit);
                break;
            case R.id.send_comment:
                AddCommenthread addCommentThread = new AddCommenthread();
                addCommentThread.start();
                break;
        }
    }



    private void submit() {
        // validate
        String edit = comment_edit.getText().toString().trim();
        if (TextUtils.isEmpty(edit)) {
            Toast.makeText(this, "edit不能为空", Toast.LENGTH_SHORT).show();
        }

        // TODO validate success, do something


    }

    /*点赞的子线程*/
    private class AddNiceNumThread extends Thread {
        @Override
        public void run() {
            try {
                String path = String_Util.urlString + "AddPostNiceNum?post_id=" + post.getPost_id() + "&user_id=" + String_Util.userId;
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    final String result = baos.toString();
                    /*UI界面操作*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                post_detail_btn_up.setImageResource(R.drawable.uped);
                                int num = Integer.parseInt(up_num.getText().toString());
                                up_num.setText(String.valueOf(num + 1));
                                Toast.makeText(PostDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PostDetailActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*取消点赞的子线程*/
    private class CancelNiceNumThread extends Thread {
        @Override
        public void run() {
            try {
                String path = String_Util.urlString + "CanclePostNiceNum?post_id=" + post.getPost_id() + "&user_id=" + String_Util.userId;
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    final String result = baos.toString();
                    /*UI界面操作*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                post_detail_btn_up.setImageResource(R.drawable.up);
                                int num = Integer.parseInt(up_num.getText().toString());
                                up_num.setText(String.valueOf(num - 1));
                                Toast.makeText(PostDetailActivity.this, "取消赞", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PostDetailActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*添加评论的子线程*/
    private class AddCommenthread extends Thread {
        @Override
        public void run() {
            try {
                int postId = post.getPost_id();
                String comment_content = URLEncoder.encode(comment_edit.getText().toString(),"utf-8");
                int comment_level = 0;
                int userId = String_Util.userId;
                String path = String_Util.urlString + "AddComment?post_id=" + postId + "&comment_content=" +comment_content+"&comment_level="+comment_level+"&from_uid="+ userId;
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream is = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    final String result = baos.toString();
                    /*UI界面操作*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(PostDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                SoftInputUtil.closeSoftInputFromWindow(PostDetailActivity.this,comment_edit);
                                setAdapter();//刷新评论列表
                            } else {
                                Toast.makeText(PostDetailActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}