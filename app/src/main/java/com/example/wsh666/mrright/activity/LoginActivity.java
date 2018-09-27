package com.example.wsh666.mrright.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:

                break;
            case R.id.forgetPwd:
                Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toRegister:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginBtn:

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

        // TODO validate success, do something


    }
}
