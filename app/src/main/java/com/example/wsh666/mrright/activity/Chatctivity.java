package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.ChatListAdepter;
import com.example.wsh666.mrright.bean.Chat;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Chatctivity extends AppCompatActivity {

    Handler chat_mHandler;
    List<Chat> chatList;
    String send_user;
    String send_name;

    private ChatListAdepter chatAdapter;

    private TextView chatuser;
    private ImageView fanhui;
    /**
     * 声明ListView
     */
    private ListView lv_chat_dialog;
    /**
     * 集合
     */
    private List<Chat> personChats = new ArrayList<Chat>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_chatctivity);

        /*Intent intent = getIntent();
        send_user = intent.getStringExtra("chat_userId");*/

        /*得到点击列表传递过来的信息，包括发送人的id以及name*/
        Bundle b=this.getIntent().getExtras();
        String[] array=b.getStringArray("chat_send_information");
        send_user = array[0];
        send_name = array[1];

        /*通过线程得到消息*/
        sixin();

        lv_chat_dialog = (ListView) findViewById(R.id.lv_chat_dialog);
        Button btn_chat_message_send = (Button) findViewById(R.id.btn_chat_message_send);
        final EditText et_chat_message = (EditText) findViewById(R.id.et_chat_message);
        /**
         * 发送按钮的点击事件
         */
        btn_chat_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(et_chat_message.getText().toString())) {
                    Toast.makeText(Chatctivity.this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Chat personChat = new Chat();
                //代表自己发送
                personChat.setIs_meSend(true);
                //得到发送内容
                personChat.setChat_content(et_chat_message.getText().toString());
                /*上传信息的线程*/
                int recive = Integer.parseInt(send_user);
                AddChatThread addChatThread = new AddChatThread(et_chat_message.getText().toString(),recive,String_Util.userId);
                addChatThread.start();
                //加入集合
                chatList.add(personChat);
                //清空输入框
                et_chat_message.setText("");
                //刷新ListView
                /**
                 *setAdapter
                 */
                chatAdapter = new ChatListAdepter(Chatctivity.this, chatList);
                chatAdapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }
        });

        chatuser = (TextView)findViewById(R.id.chatname);
        chatuser.setText(send_name);

        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                finish();
            }
        });
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    /**
                     * ListView条目控制在最后一行
                     */
                    lv_chat_dialog.setSelection(chatList.size());
                    break;

                default:
                    break;
            }
        }
    };

    /*私信界面的操作*/
    public void sixin(){
        chatList = new ArrayList<>();
        GetChatThread getChatThread = new GetChatThread();
        getChatThread.start();
        /*try {
            getChatThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        GetChatNotReadThread getChatNotReadThread = new GetChatNotReadThread();
        getChatNotReadThread.start();

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
                /**
                 *setAdapter
                 */
                chatAdapter = new ChatListAdepter(Chatctivity.this, chatList);
                lv_chat_dialog.setAdapter(chatAdapter);
                handler.sendEmptyMessage(1);

                /*//修改信息的已读状态的线程
                UpdateIsReadThread updateIsReadThread  = new UpdateIsReadThread();
                updateIsReadThread.start();*/
//                for(int i=0;i<chatList.size();i++){
//                    chatList.remove(i);
//                }
            }
        };

        /*发送广播给MainActivity 消掉红点*/
        // 广播通知
        Intent intent = new Intent();
        intent.setAction("action.refreshRed");
        sendBroadcast(intent);
    }

    /*根据发送人和接收人查询消息*/
    /*得到聊天的信息线程*/
    private class GetChatThread extends Thread{
        @Override
        public void run(){
//            while(true){
                try {
//                    Thread.sleep(1000);
                    Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                    String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetChatServlet?sendId="+ send_user+"&reciveId="+String_Util.userId);
                    Gson gson = new Gson();
                    List<Chat> chats = gson.fromJson(jsonData,
                            new TypeToken<List<Chat>>() {}.getType());
                    for(Chat chat : chats) {
                        if(chat.getChat_send()==String_Util.userId){
                            chat.setIs_meSend(true);
                        }
                        Log.e("Chatdetail" , chat.toString());
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

    /*更改已读状态的线程*/
    private class UpdateIsReadThread extends Thread{
        @Override
        public void run(){
            Log.e("UpdateThread","asd");
//            while(true){
            try {
                for(int i=0;i<chatList.size();i++){
                    Log.e("update",String.valueOf(chatList.get(i).getChat_id()));
                    String path = String_Util.urlString + "UpdateChatIsRead?chat_id=" + chatList.get(i).getChat_id()+"&chat_recive="+String_Util.userId ;
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
                    /*UI界面操作*//*
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });*/
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
//            }
        }
    }

    /*根据发送人和接收人查询未读消息*/
    /*得到聊天的信息线程*/
    private class GetChatNotReadThread extends Thread{
        @Override
        public void run(){
            while(true){
            try {
                Thread.sleep(2000);
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetChatNotRead?sendId="+ send_user+"&reciveId="+String_Util.userId);
                Gson gson = new Gson();
                List<Chat> chats = gson.fromJson(jsonData,
                        new TypeToken<List<Chat>>() {}.getType());
                for(Chat chat : chats) {
                    if(chat.getChat_send()==String_Util.userId){
                        chat.setIs_meSend(true);
                    }
                    Log.e("ChatdetailNotRead" , chat.toString());
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
                //修改信息的已读状态的线程
                UpdateIsReadThread updateIsReadThread  = new UpdateIsReadThread();
                updateIsReadThread.start();
            }
        }
    }

    /*上传信息的线程*/
    private class AddChatThread extends Thread{
        private int send_id;
        private int recive_id;
        String chat_content;

         AddChatThread(String chat_content, int recive_id, int send_id) {
            this.chat_content = chat_content;
            this.recive_id = recive_id;
            this.send_id = send_id;
        }
        @Override
        public void run(){
            Log.e("AddChat","asd");
//            while(true){
            try {
                    String path = String_Util.urlString + "Addchat?send_id=" + String.valueOf(send_id)+"&recive_id="+String.valueOf(recive_id) +"&chat_content="+ URLEncoder.encode(chat_content) ;
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
//                    UI界面操作
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(Chatctivity.this, "发送成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Chatctivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    }
            }catch (Exception e) {
                e.printStackTrace();
            }
//            }
        }
    }

}
