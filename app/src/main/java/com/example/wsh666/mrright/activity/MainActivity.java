package com.example.wsh666.mrright.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.Chat;
import com.example.wsh666.mrright.bean.Reminds;
import com.example.wsh666.mrright.tab_fragment.Tab_Find_Fragment;
import com.example.wsh666.mrright.tab_fragment.Tab_Message_Fragment;
import com.example.wsh666.mrright.tab_fragment.Tab_Personal_Fragment;
import com.example.wsh666.mrright.tab_fragment.Tab_Recommed_Fragment;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created bywsh666 on 2018/9/8 13:50
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
public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, View.OnClickListener
                    ,NavigationView.OnNavigationItemSelectedListener{

    private FrameLayout frame;
    private RadioButton tab_recommed;
    private RadioButton tab_find;
    private RadioButton tab_message;
    private CircleImageView n;
    private RadioButton tab_personal;
    private RadioGroup bottom_bar;
    private ImageView show_dialog;

    private FragmentManager fragmentManager;
    private Fragment f_recommed, f_find, f_message, f_personal;

    private Fragment write_post_fragment;

    private Dialog mCameraDialog;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;

    private TextView my_name;
    private TextView my_email;
    private CircleImageView head_image;

    private FrameLayout fill_fragment;

    Handler chat_mHandler;
    List<Chat> chatList;

    Handler remind_mHandler;
    List<Reminds> remindList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);*/

        /*沉浸式状态栏，不需要设置主题啥的了*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();

        /*接收ChatActivity的广播*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshRed");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        initView();

        sixin();
        tixing();
    }

    private void initView() {
        frame = (FrameLayout) findViewById(R.id.frame);
        tab_recommed = (RadioButton) findViewById(R.id.tab_recommed);
        tab_find = (RadioButton) findViewById(R.id.tab_find);
        tab_message = (RadioButton) findViewById(R.id.tab_message);
        n = (CircleImageView) findViewById(R.id.n);
        tab_personal = (RadioButton) findViewById(R.id.tab_personal);
        bottom_bar = (RadioGroup) findViewById(R.id.bottom_bar);
        show_dialog = (ImageView) findViewById(R.id.show_dialog);
        fill_fragment=(FrameLayout)findViewById(R.id.fill_fragment);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        nav_view = (NavigationView) findViewById(R.id.nav_view);

        bottom_bar.setOnCheckedChangeListener(this);
        show_dialog.setOnClickListener(this);
        tab_recommed.setChecked(true);

        /*设置RadioButton中图片的大小*/
        Drawable recommed_image = getResources().getDrawable(R.drawable.tab_recommend);
        recommed_image.setBounds(0, 0, 45, 45);
        tab_recommed.setCompoundDrawables(null, recommed_image, null, null);

        Drawable find_image = getResources().getDrawable(R.drawable.tab_find);
        find_image.setBounds(0, 0, 45, 45);
        tab_find.setCompoundDrawables(null, find_image, null, null);

        Drawable message_image = getResources().getDrawable(R.drawable.tab_message);
        message_image.setBounds(0, 0, 45, 45);
        tab_message.setCompoundDrawables(null, message_image, null, null);

        Drawable personal_image = getResources().getDrawable(R.drawable.tab_personal);
        personal_image.setBounds(0, 0, 45, 45);
        tab_personal.setCompoundDrawables(null, personal_image, null, null);

        /*滑动菜单*/
        nav_view.setCheckedItem(R.id.my_tiezi);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view.setItemIconTintList(null);//图标原有颜色

        /*滑动菜单header中的子控件*/
        View headerView = nav_view.getHeaderView(0);
        my_name=headerView.findViewById(R.id.my_name);
        my_email=headerView.findViewById(R.id.my_email);
        head_image=headerView.findViewById(R.id.my_head_image);
        my_name.setText("name");
        my_email.setText("email");
        head_image.setImageResource(R.drawable.test);
    }


    /*RadioButton点击切换事件*/
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (checkedId) {
            case R.id.tab_recommed:
                if (f_recommed == null) {
                    f_recommed = new Tab_Recommed_Fragment();
                    fragmentTransaction.add(R.id.frame, f_recommed);
                } else {
                    fragmentTransaction.show(f_recommed);
                }
                break;
            case R.id.tab_find:
                if (f_find == null) {
                    f_find = new Tab_Find_Fragment();
                    fragmentTransaction.add(R.id.frame, f_find);
                } else {
                    fragmentTransaction.show(f_find);
                }
                break;
            case R.id.tab_message:
                if (f_message == null) {
                    f_message = new Tab_Message_Fragment();
                    fragmentTransaction.add(R.id.frame, f_message);
                } else {
                    fragmentTransaction.show(f_message);
                }
                break;
            case R.id.tab_personal:
                if (f_personal == null) {
                    f_personal = new Tab_Personal_Fragment();
                    fragmentTransaction.add(R.id.frame, f_personal,"personal");
                } else {
                    fragmentTransaction.show(f_personal);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /*设置底部dialog*/
    private void setDialog() {
       /* Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);*/
        mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.dialog_bottom, null);
        //初始化视图
        rootView.findViewById(R.id.btn_share).setOnClickListener(this);
        rootView.findViewById(R.id.btn_camare).setOnClickListener(this);
        rootView.findViewById(R.id.btn_answer).setOnClickListener(this);
        rootView.findViewById(R.id.btn_serch).setOnClickListener(this);
        rootView.findViewById(R.id.close_dialog).setOnClickListener(this);
        mCameraDialog.setContentView(rootView);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        rootView.measure(0, 0);
        lp.height = rootView.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
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
            }

        };
    }

    /*提醒界面的操作*/
    public void tixing(){
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
            }

        };
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*底部加号图片监听事件*/
            case R.id.show_dialog:
                /*设置动画*/
                Animation animationOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_rotate_open);
                show_dialog.startAnimation(animationOpen);
                setDialog();
                break;
            /*dialog按钮点击事件*/
            case R.id.btn_share:
                mCameraDialog.dismiss();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,WritePostActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_camare:
                //选择照片按钮
                mCameraDialog.dismiss();
                Toast.makeText(this, "发跟拍", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_answer:
                //拍照按钮
                mCameraDialog.dismiss();
                Toast.makeText(this, "提问", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_serch:
                //搜索按钮
                mCameraDialog.dismiss();
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_dialog:
                //取消按钮
                mCameraDialog.dismiss();
                /*设置动画*/
                Animation animationClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_rotate_close);
                show_dialog.startAnimation(animationClose);
                break;
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (f_recommed != null) fragmentTransaction.hide(f_recommed);
        if (f_find != null) fragmentTransaction.hide(f_find);
        if (f_message != null) fragmentTransaction.hide(f_message);
        if (f_personal != null) fragmentTransaction.hide(f_personal);
    }

    /*滑动菜单子项点击事件*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.my_tiezi:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_genpai:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_pinglun:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_zanguo:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_shoucang:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_lishi:
                Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    /*全屏碎片加载*/
    //动态添加碎片
    public void replaceFragment(Fragment new_fragment, Fragment old_fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fill_fragment, new_fragment);
        /*hide一下，再次返回此碎片的时候才能调用重写的方法来实现数据刷新*/
        transaction.hide(old_fragment);
        //模拟返回栈 防止退出碎片时也退出活动
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*根据接收人得到聊天的信息线程*/
    private class GetChatByUserIdThread extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(1000);
                    Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                    String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetChatByUserId?userid="+ String_Util.userId);
                    Gson gson = new Gson();
                    List<Chat> chats = gson.fromJson(jsonData,
                            new TypeToken<List<Chat>>() {}.getType());
                    if(chats==null){

                    }else{
                        for(Chat chat : chats) {
//                            Log.e("Chat" , chat.toString());
                            if(chat.getIs_read()==0){
//                                Log.e("Is_read",chat.getSend_name());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        n.setVisibility(View.VISIBLE);
                                    }
                                });

                                // 广播通知私信界面刷新列表
                                Intent intent = new Intent();
                                intent.setAction("action.refreshPersonLetterList");
                                sendBroadcast(intent);
                            }
                            Message message=new Message();
                            message.what=1;//判断是哪个handler的请求
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("msg",chat);
                            message.setData(bundle);
                            chat_mHandler.sendMessage(message);

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*得到提醒的信息线程*/
    private class GetRemindByUserIdThread extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(1000);
                    Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                    String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetRemindByUserIdServlet?userid="+ String_Util.userId);
                    Gson gson = new Gson();
                    List<Reminds> remindses = gson.fromJson(jsonData,
                            new TypeToken<List<Reminds>>() {}.getType());
                    if(remindses==null){

                    }else{
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
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshRed"))
            {
             n.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
