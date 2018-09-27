package com.example.wsh666.mrright.activity;

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
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private EditText phonenum;
    private EditText yanzhengma;
    private TextView getYanzhengma;
    private TextView miao;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        phonenum = (EditText) findViewById(R.id.phonenum);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        getYanzhengma = (TextView) findViewById(R.id.getYanzhengma);
        miao = (TextView) findViewById(R.id.miao);
        register = (Button) findViewById(R.id.register);

        fanhui.setOnClickListener(this);
        getYanzhengma.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:

                break;
            case R.id.getYanzhengma:
                Toast.makeText(this, "获取验证码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register:

                break;
        }
    }

    private void submit() {
        // validate
        String phonenumString = phonenum.getText().toString().trim();
        if (TextUtils.isEmpty(phonenumString)) {
            Toast.makeText(this, "phonenumString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String yanzhengmaString = yanzhengma.getText().toString().trim();
        if (TextUtils.isEmpty(yanzhengmaString)) {
            Toast.makeText(this, "yanzhengmaString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
