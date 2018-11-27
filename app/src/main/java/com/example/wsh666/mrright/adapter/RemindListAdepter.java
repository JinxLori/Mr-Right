package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.Reminds;

import java.util.List;

/**
 * Created by wsh666 on 2018/9/27.
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

public class RemindListAdepter extends BaseAdapter {
    List<Reminds> remindList;
    Context context;

    public RemindListAdepter(List<Reminds> remindList, Context context) {
        this.remindList = remindList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return remindList.size();
    }

    @Override
    public Object getItem(int i) {
        return remindList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Reminds remind=remindList.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.remind_listitem, viewGroup, false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder=(ViewHolder) view.getTag();
        /*viewHolder.item_image.setImageResource(remind.getImageId());*/
        viewHolder.remind_name.setText(remind.getRemind_from_name());
        viewHolder.remind_content.setText(remind.getRemind_content());
        viewHolder.remind_reason.setText(remind.getRemind_reason_content());
        return view;
    }

    class ViewHolder {
        View rootView;
        TextView remind_name;
        TextView remind_content;
        TextView remind_reason;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.remind_name = (TextView) rootView.findViewById(R.id.remind_name);
            this.remind_content = (TextView) rootView.findViewById(R.id.remind_content);
            this.remind_reason = (TextView) rootView.findViewById(R.id.remind_reason);
        }

    }
}
