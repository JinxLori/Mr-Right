package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wsh666 on 2018/11/19.
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

public class ChatListAdepter extends BaseAdapter{

    private Context context;
    private List<Chat> lists;

    public ChatListAdepter(Context context, List<Chat> lists) {
        super();
        this.context = context;
        this.lists = lists;
    }

    /**
     * 是否是自己发送的消息
     *
     * @author cyf
     *
     */
    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;// 收到对方的消息
        int IMVT_TO_MSG = 1;// 自己发送出去的消息
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    /**
     * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
     */
    public int getItemViewType(int position) {
        Chat entity = lists.get(position);

        if (entity.is_meSend()) {// 收到的消息
            return IMsgViewType.IMVT_COM_MSG;
        } else {// 自己发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        Chat entity = lists.get(arg0);
        boolean isMeSend = entity.is_meSend();
        if (holderView == null) {
            holderView = new HolderView();
            if (isMeSend) {
                arg1 = View.inflate(context, R.layout.chat_right_item,
                        null);
                holderView.tv_chat_me_message = (TextView) arg1.findViewById(R.id.tv_chat_me_message);
                holderView.me_headimage = (CircleImageView) arg1.findViewById(R.id.iv_chat_imagr_right);
                holderView.tv_chat_me_message.setText(entity.getChat_content());
                Glide.with(context).load(entity.getRecive_headimage()).into(holderView.me_headimage);
            } else {
                arg1 = View.inflate(context, R.layout.chat_left_item,
                        null);
                holderView.tv_chat_send_message = (TextView)arg1.findViewById(R.id.tvname);
                holderView.send_headimage = (CircleImageView) arg1.findViewById(R.id.ivicon);
                holderView.tv_chat_send_message.setText(entity.getChat_content());
                Glide.with(context).load(entity.getSend_headimage()).into(holderView.send_headimage);
            }
            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
        }

        return arg1;
    }

    class HolderView {
        TextView tv_chat_me_message;
        TextView tv_chat_send_message;
        CircleImageView me_headimage;
        CircleImageView send_headimage;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
