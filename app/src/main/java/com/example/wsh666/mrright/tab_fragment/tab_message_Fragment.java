package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.Chatctivity;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;
import com.example.wsh666.mrright.adapter.PersonalLetterListAdepter;
import com.example.wsh666.mrright.adapter.RemindListAdepter;
import com.example.wsh666.mrright.bean.Chat;
import com.example.wsh666.mrright.bean.Reminds;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created bywsh666 on 2018/9/9 15:19
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
public class Tab_Message_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener{


    private TextView tv_one;
    private TextView tv_two;
    private ImageView img_cursor;
    private ViewPager vpager_four;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离

    Handler chat_mHandler;
    List<Chat> chatList;
    ListView sixin_listView;

    Handler remind_mHandler;
    List<Reminds> remindList;
    ListView remindListView;

    public Tab_Message_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_message_, container, false);

        /*注册接收广播*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshPersonLetterList");
        getActivity().registerReceiver(refreshPersonLetterListReceiver, intentFilter);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);
        vpager_four = (ViewPager) view.findViewById(R.id.vpager_four);

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3

        /*初始化ViewPager的几个页面*/
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        View view_tixing=mInflater.inflate(R.layout.view_tixing,null);
        View view_sixin=mInflater.inflate(R.layout.view_sixin,null);

        /*view_sixin 的响应事件*/
        /*//数据填充
        PersonaLetter p1=new PersonaLetter(R.drawable.test,"user1","2222","刚刚");
        PersonaLetter p2=new PersonaLetter(R.drawable.test,"user1","2222","刚刚");
        final List<PersonaLetter> personaLetterList = new ArrayList<>();
        personaLetterList.add(p1);
        personaLetterList.add(p2);*/
        sixin_listView = view_sixin.findViewById(R.id.sixinList);
        sixin();
        //点击事件设置
        sixin_listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),chatList.get(i).getSend_name() , Toast.LENGTH_SHORT).show();
                String chatSend = String.valueOf(chatList.get(i).getChat_send());
                String chatName = chatList.get(i).getSend_name();
                Bundle b = new Bundle();
                b.putStringArray("chat_send_information",new String[]{chatSend,chatName});
                Intent intent  = new Intent();
                intent.setClass(getActivity(), Chatctivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        sixin_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "弹出菜单", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        /*view_remind 响应事件*/
        /*//数据填充
        final List<Remind> remindList=new ArrayList<>();
        Remind r1=new Remind(R.drawable.test,"user1","被操作的内容1");
        Remind r2=new Remind(R.drawable.test,"user2","被操作的内容2");
        remindList.add(r1);
        remindList.add(r2);*/
        remindListView=view_tixing.findViewById(R.id.tixingList);
        tixin();
        /*//适配器设置
        RemindListAdepter remindListAdapter=new RemindListAdepter(remindList,getActivity());
        remindListView.setAdapter(remindListAdapter);*/
        remindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), remindList.get(i).getRemind_from_name(), Toast.LENGTH_SHORT).show();
            }
        });
        remindListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "弹出菜单", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        /*往ViewPager填充View，同时设置点击事件与页面切换事件*/
        listViews = new ArrayList<View>();
        listViews.add(view_tixing);
        listViews.add(view_sixin);
        /*设置ViewPager的适配器*/
        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);          //设置ViewPager当前页，从0开始算
        tv_one.setTextColor(getResources().getColor(R.color.green));

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);

        vpager_four.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                vpager_four.setCurrentItem(0);
                break;
            case R.id.tv_two:
                vpager_four.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageSelected(int index) {
        chageTextColor();
        Animation animation = null;
        switch (index) {
            case 0:
                tv_one.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                }
                break;
            case 1:
                tv_two.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                }
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /*更改顶部选项字体颜色*/
    public void chageTextColor(){
        tv_one.setTextColor(getResources().getColor(R.color.small_black));
        tv_two.setTextColor(getResources().getColor(R.color.small_black));
    }

    /*私信界面的操作*/
    public void sixin(){
        chatList = new ArrayList<>();
        GetChatByUserIdThread getChatByUserIdThread = new GetChatByUserIdThread();
        getChatByUserIdThread.start();
        chat_mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Chat chat = new Chat();
                        chat = (Chat) msg.getData().getSerializable("msg");
                        chatList.add(chat);
                        break;
                    default:
                        break;
                }
                PersonalLetterListAdepter personalLetterListAdepter=new PersonalLetterListAdepter(chatList,getActivity());
                sixin_listView.setAdapter(personalLetterListAdepter);
            }

        };
    }

    /*根据接收人得到聊天的信息线程*/
    private class GetChatByUserIdThread extends Thread{
        @Override
        public void run(){
//            while(true){
                try {
                    Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                    String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetChatByUserId?userid="+ String_Util.userId);
                    Gson gson = new Gson();
                    List<Chat> chats = gson.fromJson(jsonData,
                            new TypeToken<List<Chat>>() {}.getType());
                    for(Chat chat : chats) {
                        Log.e("ChatList" , chat.toString());
                        if(chat.getIs_read()==0){
                            Log.e("Is_read",chat.getSend_name());
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tab_message.setText("111");
                                }
                            });*/
                        }
                        Message message=new Message();
                        message.what=1;//判断是哪个handler的请求
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("msg",chat);
                        message.setData(bundle);
                        chat_mHandler.sendMessage(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }

        }
    }


    /*提醒界面的操作*/
    public void tixin(){
        remindList = new ArrayList<>();
        GetRemindByUserIdThread getRemindByUserIdThread = new GetRemindByUserIdThread();
        getRemindByUserIdThread.start();
        remind_mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Reminds remind = new Reminds();
                        remind = (Reminds) msg.getData().getSerializable("msg");
                        remindList.add(remind);
                        break;
                    default:
                        break;
                }
                //适配器设置
        RemindListAdepter remindListAdapter=new RemindListAdepter(remindList,getActivity());
        remindListView.setAdapter(remindListAdapter);
            }

        };
    }

    /*根据接收人得到聊天的信息线程*/
    private class GetRemindByUserIdThread extends Thread{
        @Override
        public void run(){
//            while(true){
            try {
                Thread.sleep(1000);
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetRemindByUserIdServlet?userid="+ String_Util.userId);
                Gson gson = new Gson();
                List<Reminds> remindses = gson.fromJson(jsonData,
                        new TypeToken<List<Reminds>>() {}.getType());
                for(Reminds remind : remindses) {
//                        Log.e("Remind" , remind.toString());
                    if(remind.getIs_read()==0){
//                            Log.e("Is_read",remind.getRemind_content());
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tab_message.setText("111");
                                }
                            });*/
                    }
                    Message message=new Message();
                    message.what=1;//判断是哪个handler的请求
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("msg",remind);
                    message.setData(bundle);
                    remind_mHandler.sendMessage(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }

        }
    }

    // 注册广播
    private BroadcastReceiver refreshPersonLetterListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshPersonLetterList"))
            {
                sixin();
            }
        }
    };

    /*销毁碎片的同时销毁掉注册的广播*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(refreshPersonLetterListReceiver);
    }

}
