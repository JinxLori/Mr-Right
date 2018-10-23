package com.example.wsh666.mrright.adapter;

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

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.CommentDetailActivity;
import com.example.wsh666.mrright.bean.Comment;

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
    int flag = 1;

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
        viewHolder.comment_up_num.setText(String.valueOf(commentList.get(i).getComment_up_num()));
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
        /*没有子级评论是隐藏查看更多评论字眼*/
        if(commentList.get(i).getSecond_comment_num()==0){
            viewHolder.more_comment.setVisibility(View.GONE);
        }
        /*判断该用户对于此评论是否已经实现过点赞*/
        if(commentList.get(i).getIs_nice().equals("true")){
            viewHolder.comment_up.setImageResource(R.drawable.uped);
        }
        return view;
    }

    class ViewHolder {
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

        public ViewHolder(View rootView) {
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
