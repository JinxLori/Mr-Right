package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.User;
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


/**
 * Created bywsh666 on 2018/9/23 16:16
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
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private EditText userName;
    private EditText pwd;
    private TextView forgetPwd;
    private TextView toRegister;
    private Button loginBtn;
    private TextView toYanzhengmaLogin;
    private ImageView qqLogin;
    private ImageView weiboLogin;
    private ImageView weixinLogin;

    private Handler mHandler;
    User user1 = new User();//查询到的登录用户的信息

    //声明Sharedpreferenced对象
    private SharedPreferences sp ;

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
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        userName = (EditText) findViewById(R.id.userName);
        pwd = (EditText) findViewById(R.id.pwd);
        forgetPwd = (TextView) findViewById(R.id.forgetPwd);
        toRegister = (TextView) findViewById(R.id.toRegister);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        toYanzhengmaLogin = (TextView) findViewById(R.id.toYanzhengmaLogin);
        qqLogin = (ImageView) findViewById(R.id.qqLogin);
        weiboLogin = (ImageView) findViewById(R.id.weiboLogin);
        weixinLogin = (ImageView) findViewById(R.id.weixinLogin);

        fanhui.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        toYanzhengmaLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        weiboLogin.setOnClickListener(this);
        weixinLogin.setOnClickListener(this);

        /*得到当前登录用户的信息user1*/
        final List<User> userList = new ArrayList<>();
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        User user = new User();
                        user = (User) msg.getData().getSerializable("msg");
                        userList.add(user);
                        user1 = userList.get(0);
                        /*将登陆者的信息存在全局静态变量中*/
                        String_Util.userHeadimage = user1.getHeadimage();
                        String_Util.userId = user1.getUserid();
                        String_Util.username = user1.getUsername();

                        /*将用户信息存入缓存*/
                        /**
                         * 获取SharedPreferenced对象
                         * 第一个参数是生成xml的文件名
                         * 第二个参数是存储的格式（**注意**本文后面会讲解）
                         */
                        sp = getSharedPreferences("User", Context.MODE_PRIVATE);
                        //获取到edit对象
                        SharedPreferences.Editor edit = sp.edit();
                        //通过editor对象写入数据
                        edit.putString("username",user1.getUsername());//提交数据存入到xml文件中
                        edit.putString("password",user1.getPassword());
                        edit.putString("headimage",user1.getHeadimage());
                        edit.putInt("userid",user1.getUserid());
                        edit.commit();

                        /*String value = sp.getString("headimage","Null");
                        Toast.makeText(LoginActivity.this, value, Toast.LENGTH_SHORT).show();*/

                        // 广播通知即将返回去的用户信息界面更改显示内容
                        Intent broadcast = new Intent();
                        broadcast.setAction("action.refreshUser");
                        sendBroadcast(broadcast);

                        /*登陆成功返回*/
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
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fanhui:
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
                break;
            case R.id.forgetPwd:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toRegister:
                intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:
                submit();
                /*先通过用户输入的账号密码得到用户id*/
                LoginThread loginThread = new LoginThread();
                loginThread.start();
                try {
                    loginThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*根据用户id查询用户的信息，并存在全局静态变量中*/
                GetUserByUserIDThread getUserByUserIDThread = new GetUserByUserIDThread();
                getUserByUserIDThread.start();
                /*跳转回用户信息界面，也就是第四个界面*/

                break;
            case R.id.toYanzhengmaLogin:
                Toast.makeText(this, "验证码登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.qqLogin:
                Toast.makeText(this, "QQ登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weiboLogin:
                Toast.makeText(this, "微博登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weixinLogin:
                Toast.makeText(this, "微信登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void submit() {
        // validate
        String userNameString = userName.getText().toString().trim();
        if (TextUtils.isEmpty(userNameString)) {
            Toast.makeText(this, "userNameString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwdString = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdString)) {
            Toast.makeText(this, "pwdString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /*登录（子线程）*/
    private class LoginThread extends Thread{
        @Override
        public void run() {
            try {
                String username= URLEncoder.encode(userName.getText().toString());
                String password = pwd.getText().toString();
//               /*这里要将中文当做数据传入URL，需要先对其进行编码，不然传递过去的是乱码*//*
                /*String post_content = URLEncoder.encode(edit_post.getText().toString(),"utf-8");*/
                String path = String_Util.urlString + "LoginServlet?username="+username+"&password="+password;
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
                    if (result.equals("0")) {/*登录错误*/
                        String_Util.userId = Integer.parseInt(result);
                        Log.e(result,String.valueOf(String_Util.userId));
                    } else {/*登陆成功*/
                        String_Util.userId = Integer.parseInt(result);
                    }
                    /*UI界面操作*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                                Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "登陆中错误", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    /*查询登录用户的信息的线程*/
    private class GetUserByUserIDThread extends Thread {
        @Override
        public void run() {
            Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
            String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetUserByUserId?userid="+ String_Util.userId);
            Gson gson = new Gson();
            List<User> users = gson.fromJson(jsonData,
                    new TypeToken<List<User>>() {}.getType());
            if(users==null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                for(User user : users) {
                    Log.e("登录中查询用户信息的线程" , user.toString());
                    Message message=new Message();
                    message.what=1;//判断是哪个handler的请求
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("msg",user);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        }
    }
}
