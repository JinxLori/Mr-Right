package com.example.wsh666.mrright.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.OtherUserActivity;
import com.example.wsh666.mrright.activity.PostDetailActivity;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.my_view.MyImageDialog;
import com.example.wsh666.mrright.util.String_Util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

public class PostListAdapter extends BaseAdapter {
    List<Post> postList = new ArrayList<Post>();
    private LayoutInflater layoutInflater;
    private Context context;

    public PostListAdapter(List<Post> postList, Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.postList = postList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.recommend_one_listitem, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        Glide.with(context).load(postList.get(i).getHeadimage()).into(viewHolder.list_head_image);
        viewHolder.list_username.setText(postList.get(i).getUsername());
        viewHolder.list_close.setImageResource(R.drawable.chacha);
        viewHolder.post_content.setText(postList.get(i).getPost_content_text());
        viewHolder.post_topic.setText(postList.get(i).getTopic_content());

        /*GridView数据填充处理*/
        final List<String> imagesAddress = Arrays.asList(postList.get(i).getPost_content_image().split(","));
        PostGridViewAdapter postGridViewAdapter = new PostGridViewAdapter(context,imagesAddress);
        viewHolder.images.setAdapter(postGridViewAdapter);
        if(postList.get(i).getPost_content_image().equals("")){
            viewHolder.images.setVisibility(View.GONE);
        }else {
            Log.e("PostListAdapter",imagesAddress.get(0)+" ");
            viewHolder.images.setVisibility(View.VISIBLE);
        }

        viewHolder.list_btn_share.setImageResource(R.drawable.zhuanfa);
        viewHolder.list_btn_pinglun.setImageResource(R.drawable.pinglun);
        viewHolder.list_btn_up.setImageResource(R.drawable.up);
        viewHolder.list_btn_down.setImageResource(R.drawable.down);
        viewHolder.share_num.setText(String.valueOf(postList.get(i).getZhuanfa_num()));
        viewHolder.pinglun_num.setText(String.valueOf(postList.get(i).getPost_comment_num()));
        viewHolder.up_num.setText(String.valueOf(postList.get(i).getPost_nice_num()));

        /*判断该用户对于此帖子是否已经实现过点赞*/
        if(postList.get(i).getIs_nice().equals("true")){
            viewHolder.list_btn_up.setImageResource(R.drawable.uped);
        }
        /*GridView子项点击事件处理*/
        viewHolder.images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, imagesAddress.get(i), Toast.LENGTH_SHORT).show();
                MyImageDialog myImageDialog = new MyImageDialog(context,R.style.DialogAnimation,0,0,imagesAddress.get(i));
                myImageDialog.show();
            }
        });
        /*点赞事件处理*/
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.list_btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //已点赞（不可点赞）
                if(finalViewHolder.list_btn_up.getDrawable().getCurrent().getConstantState().equals(context.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    Toast.makeText(context, "不可多次点赞", Toast.LENGTH_SHORT).show();
                }else{//未点赞（可点赞）进入点赞的子线程
                    AddNiceNumThread addThread = new AddNiceNumThread(i,finalViewHolder);
                    addThread.start();
                }
            }
        });
        /*取消赞事件处理*/
        viewHolder.list_btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //已点赞(取消赞)
                if(finalViewHolder.list_btn_up.getDrawable().getCurrent().getConstantState().equals(context.getResources().getDrawable(R.drawable.uped).getConstantState())){
                    CancelNiceNumThread cancelThread = new CancelNiceNumThread(i,finalViewHolder);
                    cancelThread.start();
                }else{//未点赞，不进行操作，进入取消点赞的子线程
                    Toast.makeText(context, "没有可取消的赞", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*帖子内容点击事件*/
        viewHolder.post_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,postList.get(i).toString(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",postList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, PostDetailActivity.class);
                context.startActivity(intent);

                /*记住点击的post的下标*/
                String_Util.post_index = i;
            }
        });
        /*评论按钮点击事件*/
        viewHolder.list_btn_pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",postList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, PostDetailActivity.class);
                context.startActivity(intent);
            }
        });

        /*头像点击事件*/
        viewHolder.list_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",postList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, OtherUserActivity.class);
                context.startActivity(intent);
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
                String path = String_Util.urlString + "AddPostNiceNum?post_id=" + postList.get(i).getPost_id() + "&user_id=" + String_Util.userId;
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
                                viewHolder.list_btn_up.setImageResource(R.drawable.uped);
                                int num = Integer.parseInt(viewHolder.up_num.getText().toString());
                                viewHolder.up_num.setText(String.valueOf(num+1));
                                Toast.makeText(context, "点赞成功", Toast.LENGTH_SHORT).show();
                                //点赞状态改变，所以更改post的Is_nice，才能给活动PostDetailActivity传递过去正确的值
                                postList.get(i).setIs_nice("true");
                                postList.get(i).setPost_nice_num(num+1);
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
                String path = String_Util.urlString + "CanclePostNiceNum?post_id=" + postList.get(i).getPost_id() + "&user_id=" + String_Util.userId;
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
                                viewHolder.list_btn_up.setImageResource(R.drawable.up);
                                int num = Integer.parseInt(viewHolder.up_num.getText().toString());
                                viewHolder.up_num.setText(String.valueOf(num-1));
                                Toast.makeText(context, "取消赞", Toast.LENGTH_SHORT).show();
                                //点赞状态改变，所以更改post的Is_nice，才能给活动PostDetailActivity传递过去正确的值
                                postList.get(i).setIs_nice("false");
                                postList.get(i).setPost_nice_num(num-1);
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
        CircleImageView list_head_image;
        TextView list_username;
        ImageView list_close;
        TextView post_content;
        TextView post_topic;
        //ImageView post_image;
        GridView images;
        ImageView list_btn_share;
        TextView share_num;
        ImageView list_btn_pinglun;
        TextView pinglun_num;
        ImageView list_btn_up;
        TextView up_num;
        ImageView list_btn_down;

       ViewHolder(View rootView) {
            this.rootView = rootView;
            this.list_head_image = (CircleImageView) rootView.findViewById(R.id.list_head_image);
            this.list_username = (TextView) rootView.findViewById(R.id.list_username);
            this.list_close = (ImageView) rootView.findViewById(R.id.list_close);
            this.post_content = (TextView) rootView.findViewById(R.id.post_content);
            this.post_topic = (TextView) rootView.findViewById(R.id.post_topic);
            //this.post_image = (ImageView) rootView.findViewById(R.id.post_image);
            this.images = (GridView) rootView.findViewById(R.id.images);
            this.list_btn_share = (ImageView) rootView.findViewById(R.id.list_btn_share);
            this.share_num = (TextView) rootView.findViewById(R.id.share_num);
            this.list_btn_pinglun = (ImageView) rootView.findViewById(R.id.list_btn_pinglun);
            this.pinglun_num = (TextView) rootView.findViewById(R.id.pinglun_num);
            this.list_btn_up = (ImageView) rootView.findViewById(R.id.list_btn_up);
            this.up_num = (TextView) rootView.findViewById(R.id.up_num);
            this.list_btn_down = (ImageView) rootView.findViewById(R.id.list_btn_down);
        }

    }
}
