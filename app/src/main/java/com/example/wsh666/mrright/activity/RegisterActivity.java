package com.example.wsh666.mrright.activity;

import android.app.Instrumentation;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.example.wsh666.mrright.util.String_Util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


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
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private EditText phonenum;
    private EditText yanzhengma;
    private EditText pwd;
    private TextView getYanzhengma;
    private TextView miao;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "89c3b2a08106abb0e960573228f51876");

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
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        phonenum = (EditText) findViewById(R.id.phonenum);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        pwd = (EditText) findViewById(R.id.password);
        getYanzhengma = (TextView) findViewById(R.id.getYanzhengma);
        miao = (TextView) findViewById(R.id.miao);
        register = (Button) findViewById(R.id.register);

        fanhui.setOnClickListener(this);
        getYanzhengma.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String phonenumString = phonenum.getText().toString().trim();
        String yanzhengmaString = yanzhengma.getText().toString().trim();
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
            case R.id.getYanzhengma:
                if (TextUtils.isEmpty(phonenumString)) {
                    Toast.makeText(this, "phonenumString不能为空", Toast.LENGTH_SHORT).show();
                }
                /*获取验证码点击效果改变*/
                TimeCount time = new TimeCount(60000,1000);
                time.start();
                /*请求发送短信验证码*/
                BmobSMS.requestSMSCode(phonenumString, "Mr'Right", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "请注意接收验证码！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "发送失败！"+e.getErrorCode() + "-" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.register:
                submit();
                BmobSMS.verifySmsCode(phonenumString, yanzhengmaString, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "验证码正确", Toast.LENGTH_SHORT).show();
                            AddUserThread addUserThread = new AddUserThread();
                            addUserThread.start();
                        } else {
                            Toast.makeText(RegisterActivity.this, "验证码错误"+e.getErrorCode() + "-" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    private void submit() {
        // validate
        String phonenumString = phonenum.getText().toString().trim();
        if (TextUtils.isEmpty(phonenumString)) {
            Toast.makeText(this, "phonenumString不能为空", Toast.LENGTH_SHORT).show();
        }

        String yanzhengmaString = yanzhengma.getText().toString().trim();
        if (TextUtils.isEmpty(yanzhengmaString)) {
            Toast.makeText(this, "yanzhengmaString不能为空", Toast.LENGTH_SHORT).show();
        }

        String pwdString = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /*注册（子线程）*/
    private class AddUserThread extends Thread{
        @Override
        public void run() {
            try {
                String username= phonenum.getText().toString();
                String password = pwd.getText().toString();
                String phonenumber = username;
                String headimage = "";
                String sex = "m";
                String sign = "";
                String birthday = "";
//               /*这里要将中文当做数据传入URL，需要先对其进行编码，不然传递过去的是乱码*//*
                /*String post_content = URLEncoder.encode(edit_post.getText().toString(),"utf-8");*/
//
                String path = String_Util.urlString + "AddUser?username="+username+"&password="+password+"&headimage="+headimage+"&phonenumber="+phonenumber+"&sex="+sex+"&birthday="+birthday+"&sign="+sign;
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("1")) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*60秒倒计时*/
    class TimeCount extends CountDownTimer {
         TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getYanzhengma.setBackgroundColor(Color.parseColor("#B6B6D8"));
            getYanzhengma.setClickable(false);
            getYanzhengma.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            getYanzhengma.setText("重新获取验证码");
            getYanzhengma.setClickable(true);
            getYanzhengma.setBackgroundColor(Color.parseColor("#4EB84A"));
        }
    }
}
