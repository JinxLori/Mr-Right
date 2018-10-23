package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.PostDetailActivity;
import com.example.wsh666.mrright.bean.Post;

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
        viewHolder.list_head_image.setImageResource(R.drawable.test);
        viewHolder.list_username.setText(postList.get(i).getUser_name());
        viewHolder.list_close.setImageResource(R.drawable.chacha);
        viewHolder.post_content.setText(postList.get(i).getPost_content_text());
        viewHolder.post_topic.setText(postList.get(i).getPost_topic());
        viewHolder.post_image.setImageResource(R.drawable.test);
        viewHolder.list_btn_share.setImageResource(R.drawable.zhuanfa);
        viewHolder.list_btn_pinglun.setImageResource(R.drawable.pinglun);
        viewHolder.list_btn_up.setImageResource(R.drawable.up);
        viewHolder.list_btn_down.setImageResource(R.drawable.down);
        viewHolder.share_num.setText(String.valueOf(postList.get(i).getZhuanfa_num()));
        viewHolder.pinglun_num.setText(String.valueOf(postList.get(i).getPost_comment_num()));
        viewHolder.up_num.setText(String.valueOf(postList.get(i).getPost_nice_num()));

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
            }
        });
        return view;
    }

   class ViewHolder {
        View rootView;
        CircleImageView list_head_image;
        TextView list_username;
        ImageView list_close;
        TextView post_content;
        TextView post_topic;
        ImageView post_image;
        ImageView list_btn_share;
        TextView share_num;
        ImageView list_btn_pinglun;
        TextView pinglun_num;
        ImageView list_btn_up;
        TextView up_num;
        ImageView list_btn_down;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.list_head_image = (CircleImageView) rootView.findViewById(R.id.list_head_image);
            this.list_username = (TextView) rootView.findViewById(R.id.list_username);
            this.list_close = (ImageView) rootView.findViewById(R.id.list_close);
            this.post_content = (TextView) rootView.findViewById(R.id.post_content);
            this.post_topic = (TextView) rootView.findViewById(R.id.post_topic);
            this.post_image = (ImageView) rootView.findViewById(R.id.post_image);
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
