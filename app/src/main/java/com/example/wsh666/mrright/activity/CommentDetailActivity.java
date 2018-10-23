package com.example.wsh666.mrright.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.CommentListAdepter;
import com.example.wsh666.mrright.bean.Comment;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private CircleImageView comment_head_image;
    private TextView comment_username;
    private TextView comment_date;
    private ImageView comment_up;
    private TextView comment_up_num;
    private ImageView comment_down;
    private TextView comment_content;
    private ListView second_comment_listview;

    Handler mHandler;
    Comment comment = new Comment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        fanhui.setOnClickListener(this);
        comment_head_image.setOnClickListener(this);
        comment_username.setOnClickListener(this);
        comment_up.setOnClickListener(this);
        comment_down.setOnClickListener(this);

        /*得到传递过来的comment*/
        Bundle bundle = this.getIntent().getExtras();
        comment = (Comment) bundle.getSerializable("comment");

        comment_head_image.setImageResource(R.drawable.test);
        comment_username.setText(comment.getUsername());
        comment_date.setText(comment.getComment_date());
        comment_up.setImageResource(R.drawable.up);
        comment_up_num.setText(String.valueOf(comment.getComment_nice_num()));
        comment_down.setImageResource(R.drawable.down);
        comment_content.setText(comment.getComment_content());
        if(comment.getIs_nice().equals("true")){
            comment_up.setImageResource(R.drawable.uped);
        }
    }

    public void  setAdapter(){
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

                        CommentListAdepter commentListAdepter = new CommentListAdepter(commentList,CommentDetailActivity.this);
                        second_comment_listview.setAdapter(commentListAdepter);
                        break;
                    default:
                        break;
                }
            }
        };
        /*CommentListAdepter commentListAdepter = new CommentListAdepter(commentList,this);
        second_comment_listview.setAdapter(commentListAdepter);*/
        new Thread(){
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetCommentByCommentId?comment_id="+comment.getComment_id()+"&user_id="+String_Util.userId);
                Gson gson = new Gson();
                List<Comment> comments = gson.fromJson(jsonData,
                        new TypeToken<List<Comment>>() {}.getType());
                for(Comment comment : comments) {
                    Log.e("Thread" , comment.toString());
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
            case R.id.comment_head_image:

                break;
            case R.id.comment_username:

                break;
            case R.id.comment_up:
                //已点赞（不可点赞）
                if(comment_up.getDrawable().getCurrent().getConstantState().equals(CommentDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    Toast.makeText(CommentDetailActivity.this, "不可多次点赞", Toast.LENGTH_SHORT).show();
                }else{//未点赞（可点赞）进入点赞的子线程
                    AddNiceNumThread addThread = new AddNiceNumThread();
                    addThread.start();
                }
                break;
            case R.id.comment_down:
                //已点赞(取消赞)
                if(comment_up.getDrawable().getCurrent().getConstantState().equals(CommentDetailActivity.this.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    CancelNiceNumThread cancelThread = new CancelNiceNumThread();
                    cancelThread.start();
                }else{//未点赞，不进行操作，进入取消点赞的子线程
                    Toast.makeText(CommentDetailActivity.this, "没有可取消的赞", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /*点赞的子线程*/
    private class AddNiceNumThread extends Thread{
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
                                comment_up_num.setText(String.valueOf(num+1));
                                Toast.makeText(CommentDetailActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
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
    private class CancelNiceNumThread extends Thread{
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
                                comment_up_num.setText(String.valueOf(num-1));
                                Toast.makeText(CommentDetailActivity.this, "取消赞", Toast.LENGTH_SHORT).show();
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


}
