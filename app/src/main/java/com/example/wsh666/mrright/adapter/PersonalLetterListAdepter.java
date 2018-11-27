package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

public class PersonalLetterListAdepter extends BaseAdapter {
    List<Chat> personaLetterList;
    Context context;

    public PersonalLetterListAdepter(List<Chat> personaLetterList, Context context) {
        this.personaLetterList = personaLetterList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personaLetterList.size();
    }

    @Override
    public Object getItem(int i) {
        return personaLetterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Chat personaLetter=personaLetterList.get(i);
        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.personal_letter_listitem, viewGroup, false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder=(ViewHolder) view.getTag();
//        viewHolder.item_image.setImageResource(personaLetter.getSend_headimage());
        viewHolder.user.setText(personaLetter.getSend_name());
        viewHolder.one_message.setText(personaLetter.getChat_content());
        viewHolder.date.setText(personaLetter.getChat_time());
        Glide.with(context).load(personaLetter.getSend_headimage()).into(viewHolder.item_image);

        viewHolder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "用户详情", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder {
        View rootView;
        CircleImageView item_image;
        TextView user;
        TextView one_message;
        TextView date;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.item_image = (CircleImageView) rootView.findViewById(R.id.item_image);
            this.user = (TextView) rootView.findViewById(R.id.user);
            this.one_message = (TextView) rootView.findViewById(R.id.one_message);
            this.date = (TextView) rootView.findViewById(R.id.date);
        }

    }
}
