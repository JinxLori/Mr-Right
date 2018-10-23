package com.example.wsh666.mrright.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.CommentDetailActivity;
import com.example.wsh666.mrright.bean.Comment;
import com.example.wsh666.mrright.util.String_Util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class CommentListAdepter extends BaseAdapter {
    List<Comment> commentList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    Handler mHandler;

    public CommentListAdepter(List<Comment> commentList, Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.comment_listitem, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        viewHolder.comment_head_image.setImageResource(R.drawable.test);
        viewHolder.comment_username.setText(commentList.get(i).getUsername());
        viewHolder.comment_date.setText(commentList.get(i).getComment_date());
        viewHolder.comment_up.setImageResource(R.drawable.up);
        viewHolder.comment_down.setImageResource(R.drawable.down);
        viewHolder.comment_up_num.setText(String.valueOf(commentList.get(i).getComment_nice_num()));
        viewHolder.comment_content.setText(String.valueOf(commentList.get(i).getComment_content()));
        viewHolder.comment_more_num.setText(String.valueOf(commentList.get(i).getSecond_comment_num()));
        viewHolder.more_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("comment",commentList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, CommentDetailActivity.class);
                context.startActivity(intent);
            }
        });
        /*没有子级评论shi隐藏查看更多评论字眼*/
        if(commentList.get(i).getSecond_comment_num()==0){
            viewHolder.more_comment.setVisibility(View.GONE);
        }
        /*判断该用户对于此评论是否已经实现过点赞*/
        if(commentList.get(i).getIs_nice().equals("true")){
            viewHolder.comment_up.setImageResource(R.drawable.uped);
        }
        /*点赞事件处理*/
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.comment_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //已点赞（不可点赞）
                if(finalViewHolder.comment_up.getDrawable().getCurrent().getConstantState().equals(context.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    Toast.makeText(context, "不可多次点赞", Toast.LENGTH_SHORT).show();
                }else{//未点赞（可点赞）进入点赞的子线程
                    AddNiceNumThread addThread = new AddNiceNumThread(i,finalViewHolder);
                    addThread.start();
                }
            }
        });
        viewHolder.comment_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //已点赞(取消赞)
                if(finalViewHolder.comment_up.getDrawable().getCurrent().getConstantState().equals(context.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    CancelNiceNumThread cancelThread = new CancelNiceNumThread(i,finalViewHolder);
                    cancelThread.start();
                }else{//未点赞，不进行操作，进入取消点赞的子线程
                    Toast.makeText(context, "没有可取消的赞", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    /*点赞的子线程*/
    private class AddNiceNumThread extends Thread{
        private int i;
        private ViewHolder viewHolder;
        /*构造函数，传递列表的item和viewholder*/
        AddNiceNumThread( int i,ViewHolder viewHolder) {
            this.i = i;
            this.viewHolder = viewHolder;
        }
        @Override
        public void run() {
            try {
                String path = String_Util.urlString + "AddCommentNiceNum?comment_id=" + commentList.get(i).getComment_id() + "&user_id=" + String_Util.userId;
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
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                viewHolder.comment_up.setImageResource(R.drawable.uped);
                                int num = Integer.parseInt(viewHolder.comment_up_num.getText().toString());
                                viewHolder.comment_up_num.setText(String.valueOf(num+1));
                                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                                //点赞状态改变，所以更改comment的Is_nice，才能给活动CommentDetailActivity传递过去正确的值
                                commentList.get(i).setIs_nice("true");
                                commentList.get(i).setComment_nice_num(num+1);
                            } else {
                                Toast.makeText(context, "点赞失败", Toast.LENGTH_SHORT).show();
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
        private int i;
        private ViewHolder viewHolder;
        /*构造函数，传递列表的item和viewholder*/
        CancelNiceNumThread( int i,ViewHolder viewHolder) {
            this.i = i;
            this.viewHolder = viewHolder;
        }
        @Override
        public void run() {
            try {
                String path = String_Util.urlString + "CancleCommentNiceNum?comment_id=" + commentList.get(i).getComment_id() + "&user_id=" + String_Util.userId;
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
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                viewHolder.comment_up.setImageResource(R.drawable.up);
                                int num = Integer.parseInt(viewHolder.comment_up_num.getText().toString());
                                viewHolder.comment_up_num.setText(String.valueOf(num-1));
                                Toast.makeText(context, "取消赞", Toast.LENGTH_SHORT).show();
                                //点赞状态改变，所以更改comment的Is_nice，才能给活动CommentDetailActivity传递过去正确的值
                                commentList.get(i).setIs_nice("false");
                                commentList.get(i).setComment_nice_num(num-1);
                            } else {
                                Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ViewHolder {
        View rootView;
        CircleImageView comment_head_image;
        TextView comment_username;
        TextView comment_date;
        ImageView comment_up;
        TextView comment_up_num;
        ImageView comment_down;
        TextView comment_content;
        TextView comment_more_num;
        LinearLayout more_comment;

        ViewHolder(View rootView) {
            this.rootView = rootView;
            this.comment_head_image = (CircleImageView) rootView.findViewById(R.id.comment_head_image);
            this.comment_username = (TextView) rootView.findViewById(R.id.comment_username);
            this.comment_date = (TextView) rootView.findViewById(R.id.comment_date);
            this.comment_up = (ImageView) rootView.findViewById(R.id.comment_up);
            this.comment_up_num = (TextView) rootView.findViewById(R.id.comment_up_num);
            this.comment_down = (ImageView) rootView.findViewById(R.id.comment_down);
            this.comment_content = (TextView) rootView.findViewById(R.id.comment_content);
            this.comment_more_num = (TextView) rootView.findViewById(R.id.comment_more_num);
            this.more_comment = (LinearLayout) rootView.findViewById(R.id.more_comment);
        }
    }
}
