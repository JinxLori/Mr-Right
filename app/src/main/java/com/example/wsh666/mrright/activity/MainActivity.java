package com.example.wsh666.mrright.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.tab_fragment.tab_find_Fragment;
import com.example.wsh666.mrright.tab_fragment.tab_message_Fragment;
import com.example.wsh666.mrright.tab_fragment.tab_personal_Fragment;
import com.example.wsh666.mrright.tab_fragment.tab_recommed_Fragment;

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
public class MainActivity extends Activity implements  RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    private FrameLayout frame;
    private RadioButton tab_recommed;
    private RadioGroup bottom_bar;
    private ImageView show_dialog;

    private FragmentManager fragmentManager;
    private Fragment f_recommed,f_find,f_message,f_personal;

    private Dialog mCameraDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fragmentManager=getFragmentManager();
        initView();
    }

    private void initView() {
        frame = (FrameLayout) findViewById(R.id.frame);
        tab_recommed = (RadioButton) findViewById(R.id.tab_recommed);
        bottom_bar = (RadioGroup) findViewById(R.id.bottom_bar);
        show_dialog = (ImageView)findViewById(R.id.show_dialog);

        bottom_bar.setOnCheckedChangeListener(this);
        show_dialog.setOnClickListener(this);
        tab_recommed.setChecked(true);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (checkedId){
            case R.id.tab_recommed:
                if(f_recommed==null){
                    f_recommed=new tab_recommed_Fragment();
                    fragmentTransaction.add(R.id.frame,f_recommed);
                }else{
                    fragmentTransaction.show(f_recommed);
                }
                break;
            case R.id.tab_find:
                if(f_find==null){
                    f_find=new tab_find_Fragment();
                    fragmentTransaction.add(R.id.frame,f_find);
                }else{
                    fragmentTransaction.show(f_find);
                }
                break;
            case R.id.tab_message:
                if(f_message==null){
                    f_message=new tab_message_Fragment();
                    fragmentTransaction.add(R.id.frame,f_message);
                }else{
                    fragmentTransaction.show(f_message);
                }
                break;
            case R.id.tab_personal:
                if(f_personal==null){
                    f_personal=new tab_personal_Fragment();
                    fragmentTransaction.add(R.id.frame,f_personal);
                }else{
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*底部加号图片监听事件*/
            case R.id.show_dialog:
                setDialog();
                break;
            /*dialog按钮点击事件*/
            case R.id.btn_share:
                Toast.makeText(this, "发帖", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_camare:
                //选择照片按钮
                Toast.makeText(this, "发跟拍", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_answer:
                //拍照按钮
                Toast.makeText(this, "提问", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_serch:
                //取消按钮
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_dialog:
                //取消按钮
                mCameraDialog.dismiss();
                break;
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(f_recommed != null)fragmentTransaction.hide(f_recommed);
        if(f_find != null)fragmentTransaction.hide(f_find);
        if(f_message != null)fragmentTransaction.hide(f_message);
        if(f_personal != null)fragmentTransaction.hide(f_personal);
    }
}
