package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.CommentListAdepter;
import com.example.wsh666.mrright.bean.Comment;
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

public class CommentDetailActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemClickListener{

    private ImageView fanhui;
    private CircleImageView comment_head_image;
    private TextView comment_username;
    private TextView comment_date;
    private ImageView comment_up;
    private TextView comment_up_num;
    private ImageView comment_down;
    private TextView comment_content;
    private ListView second_comment_listview;
    private EditText comment_edit;
    private ImageView send_comment;

    Handler mHandler;
    Comment comment = new Comment();

    int flag = 0;//设置一个标记判断输入框的文字是回复楼主还是回复下面的用户，如果是回复下面的用户则改为1，评论时上传的内容将包含（回复 username ：）

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
        setContentView(R.layout.activity_comment_detail);
        initView();
        setAdapter();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        comment_head_image = (CircleImageView) findViewById(R.id.comment_head_image);
        comment_username = (TextView) findViewById(R.id.comment_username);
        comment_date = (TextView) findViewById(R.id.comment_date);
        comment_up = (ImageView) findViewById(R.id.comment_up);
        comment_up_num = (TextView) findViewById(R.id.comment_up_num);
        comment_down = (ImageView) findViewById(R.id.comment_down);
        comment_content = (TextView) findViewById(R.id.comment_content);
        second_comment_listview = (ListView) findViewById(R.id.second_comment_listview);
        comment_edit = (EditText) findViewById(R.id.comment_edit);
        send_comment = (ImageView) findViewById(R.id.send_comment);

        fanhui.setOnClickListener(this);
        comment_head_image.setOnClickListener(this);
        comment_username.setOnClickListener(this);
        comment_up.setOnClickListener(this);
        comment_down.setOnClickListener(this);
        comment_edit.setOnClickListener(this);
        send_comment.setOnClickListener(this);
        second_comment_listview.setOnItemClickListener(this);

        /*得到传递过来的comment*/
        Bundle bundle = this.getIntent().getExtras();
        comment = (Comment) bundle.getSerializable("comment");

        Glide.with(CommentDetailActivity.this).load(comment.getHeadimage()).into(comment_head_image);
        comment_username.setText(comment.getUsername());
        comment_date.setText(comment.getComment_date());
        comment_up.setImageResource(R.drawable.up);
        comment_up_num.setText(String.valueOf(comment.getComment_nice_num()));
        comment_down.setImageResource(R.drawable.down);
        comment_content.setText(comment.getComment_content());
        //动态设置输入框的提示文字
        comment_edit.setHint("回复 "+comment.getUsername()+":");
        //判断是否被点赞
        if (comment.getIs_nice().equals("true")) {
            comment_up.setImageResource(R.drawable.uped);
        }

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

                        CommentListAdepter commentListAdepter = new CommentListAdepter(commentList, CommentDetailActivity.this);
                        second_comment_listview.setAdapter(commentListAdepter);
                        break;
                    default:
                        break;
                }
            }
        };
        /*CommentListAdepter commentListAdepter = new CommentListAdepter(commentList,this);
        second_comment_listview.setAdapter(commentListAdepter);*/
        new Thread() {
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString + "GetCommentByCommentId?comment_id=" + comment.getComment_id() + "&user_id=" + String_Util.userId);
                Gson gson = new Gson();
                List<Comment> comments = gson.fromJson(jsonData,
                        new TypeToken<List<Comment>>() {
                        }.getType());
                for (Comment comment : comments) {
                    Log.e("Thread", comment.toString());
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
                Toast.makeText(CommentDetailActivity.this, "返回", Toast.LENGTH_SHORT).show();
                String_Util.comment_nice_num = Integer.parseInt(comment_up_num.getText().toString());
                            /*发送广播给PostList界面更改信息*/
                Intent braodcast = new Intent();
                braodcast.setAction("action.refreshComment");
                sendBroadcast(braodcast);
                /*返回*/
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
            case R.id.comment_head_image:
                Toast.makeText(this, "进入该用户详情", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comment_username:
                Toast.makeText(this, "进入该用户详情", Toast.LENGTH_SHORT).show();
                break;
            case R.id.comment_up:
                //已点赞（不可点赞）
                if (comment_up.getDrawable().getCurrent().getConstantState().equals(CommentDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())) {
                    Toast.makeText(CommentDetailActivity.this, "不可多次点赞", Toast.LENGTH_SHORT).show();
                } else {//未点赞（可点赞）进入点赞的子线程
                    AddNiceNumThread addThread = new AddNiceNumThread();
                    addThread.start();
                }
                break;
            case R.id.comment_down:
                //已点赞(取消赞)
                if (comment_up.getDrawable().getCurrent().getConstantState().equals(CommentDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())) {
                    CancelNiceNumThread cancelThread = new CancelNiceNumThread();
                    cancelThread.start();
                } else {//未点赞，不进行操作，进入取消点赞的子线程
                    Toast.makeText(CommentDetailActivity.this, "没有可取消的赞", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.comment_edit:
                SoftInputUtil.showSoftInputFromWindow(CommentDetailActivity.this,comment_edit);
                break;
            case R.id.send_comment:
                AddCommenthread addCommentThread = new AddCommenthread();
                addCommentThread.start();
                break;
        }
    }

    /*列表子项点击事件*/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //通过view获取其内部的组件，进而进行操作
        String text = (String) ((TextView)view.findViewById(R.id.comment_username)).getText();
        //大多数情况下，position和id相同，并且都从0开始
        /*String showText = "点击第" + position + "项，文本内容为：" + text + "，ID为：" + id;
        Toast.makeText(this, showText, Toast.LENGTH_LONG).show();*/
        comment_edit.setHint("回复 "+text+" :");//变换hint的文字
        flag = 1;
    }

    private void submit() {
        // validate
        String edit = comment_edit.getText().toString().trim();
        if (TextUtils.isEmpty(edit)) {
            Toast.makeText(this, "edit不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /*点赞的子线程*/
    private class AddNiceNumThread extends Thread {
        @Override
        public void run() {
            try {
                String path = String_Util.urlString + "AddCommentNiceNum?comment_id=" + comment.getPost_id() + "&user_id=" + String_Util.userId;
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
                                comment_up.setImageResource(R.drawable.uped);
                                int num = Integer.parseInt(comment_up_num.getText().toString());
                                comment_up_num.setText(String.valueOf(num + 1));
                                Toast.makeText(CommentDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
                                /*记录点赞状态*/
                                String_Util.comment_is_nice = true;
                            } else {
                                Toast.makeText(CommentDetailActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
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
                String path = String_Util.urlString + "CancleCommentNiceNum?comment_id=" + comment.getPost_id() + "&user_id=" + String_Util.userId;
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
                                comment_up.setImageResource(R.drawable.up);
                                int num = Integer.parseInt(comment_up_num.getText().toString());
                                comment_up_num.setText(String.valueOf(num - 1));
                                Toast.makeText(CommentDetailActivity.this, "取消赞", Toast.LENGTH_SHORT).show();
                                String_Util.comment_is_nice=false;
                            } else {
                                Toast.makeText(CommentDetailActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*添加二级评论的子线程*/
    private class AddCommenthread extends Thread {
        @Override
        public void run() {
            try {
                int postId = comment.getPost_id();
                /*根据flag判断输入框的评论是回复评论还是直接评论
                * 如果是回复评论，前面添加上hint文字，也就是“回复 xxx :”*/
                String comment_content = null;
                if(flag == 0){
                    comment_content = URLEncoder.encode(comment_edit.getText().toString(),"utf-8");
                }else{
                    comment_content = URLEncoder.encode(comment_edit.getHint().toString()+comment_edit.getText().toString(),"utf-8");
                }
                int comment_level = comment.getComment_id();
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
                                Toast.makeText(CommentDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                SoftInputUtil.closeSoftInputFromWindow(CommentDetailActivity.this,comment_edit);
                                setAdapter();//刷新评论列表
                                /*添加一个评论就记录一下*/
                                String_Util.added_comment_comment_num = String_Util.added_comment_comment_num+1;
                            } else {
                                Toast.makeText(CommentDetailActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
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
