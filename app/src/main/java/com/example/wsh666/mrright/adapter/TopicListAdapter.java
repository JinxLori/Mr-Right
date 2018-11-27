package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.Topic;

import java.util.ArrayList;
import java.util.List;

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

public class TopicListAdapter extends BaseAdapter {
    List<Topic> topicList = new ArrayList<Topic>();
    private LayoutInflater layoutInflater;
    private Context context;

    public TopicListAdapter(List<Topic> topicList, Context context) {
        this.topicList = topicList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return topicList.size();
    }

    @Override
    public Object getItem(int i) {
        return topicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.topic_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        Glide.with(context).load(topicList.get(i).getTopic_image()).into(viewHolder.topic_image);
        viewHolder.topic_name.setText(topicList.get(i).getTopic_content());
        viewHolder.topic_detail.setText("'"+topicList.get(i).getTopic_content()+"'的描述!");
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView topic_image;
        public TextView topic_name;
        public TextView topic_detail;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.topic_image = (ImageView) rootView.findViewById(R.id.topic_image);
            this.topic_name = (TextView) rootView.findViewById(R.id.topic_name);
            this.topic_detail = (TextView) rootView.findViewById(R.id.topic_detail);
        }

    }
}
