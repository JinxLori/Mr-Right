package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.PostDetailActivity;
import com.example.wsh666.mrright.bean.CommentAndPost;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wsh666 on 2018/11/22.
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

public class MyCommentListAdapter extends BaseAdapter {

    List<CommentAndPost> commentList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public MyCommentListAdapter(List<CommentAndPost> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = layoutInflater.inflate(R.layout.mypinglun_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        Glide.with(context).load(String_Util.userHeadimage).into(viewHolder.comment_head_image);
        viewHolder.comment_username.setText(String_Util.username);
        viewHolder.comment_date.setText(commentList.get(i).getComment_date());
        viewHolder.comment_content.setText(commentList.get(i).getComment_content());
        viewHolder.post_detail.setText(commentList.get(i).getPost_content_text());

        viewHolder.post_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Handler mHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg)
                    {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1:
                                Post post = new Post();
                                post = (Post) msg.getData().getSerializable("msg");

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("post",post);
                                Intent intent = new Intent();
                                intent.putExtras(bundle);
                                intent.setClass(context, PostDetailActivity.class);
                                context.startActivity(intent);

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
                        String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetPostByPostId?post_id="+ commentList.get(i).getPostId()+"&user_id="+String_Util.userId);
                        Gson gson = new Gson();
                        List<Post> posts = gson.fromJson(jsonData,
                                new TypeToken<List<Post>>() {}.getType());
                        if(posts==null){

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
        });
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public CircleImageView comment_head_image;
        public TextView comment_username;
        public TextView comment_date;
        public TextView comment_content;
        public TextView post_detail;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.comment_head_image = (CircleImageView) rootView.findViewById(R.id.comment_head_image);
            this.comment_username = (TextView) rootView.findViewById(R.id.comment_username);
            this.comment_date = (TextView) rootView.findViewById(R.id.comment_date);
            this.comment_content = (TextView) rootView.findViewById(R.id.comment_content);
            this.post_detail = (TextView) rootView.findViewById(R.id.post_detail);
        }

    }
}
