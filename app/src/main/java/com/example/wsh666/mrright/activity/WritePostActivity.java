package com.example.wsh666.mrright.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.util.ArrayList;
import java.util.List;
/**
 * Created bywsh666 on 2018/9/19 13:48
 　 へ　　　　　  ／|
　　/＼7　　　 ∠＿/
　 /　│　　 ／　／
　│　Z ＿,＜　／　　 /`ヽ
　│　　　　　ヽ　　 /　　〉
　 Y　　　　　`　   /　　/
　ｲ●　､　●　　⊂⊃〈　　/
　()　 へ　　　　|　＼〈
　　>ｰ ､_　 ィ　 │ ／／
　 / へ　　 /　ﾉ＜| ＼＼
　 ヽ_ﾉ　　(_／　 │／／
　　7　　　　　　　|／
　　＞―r￣￣`ｰ―＿
 */
public class WritePostActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;


    private ImageView fanhui;
    private TextView post;
    private EditText edit_post;
    private Button chose_topic;
    private Button select_image;
    private Button clear_image;
    private TableLayout image_table;

    private List<ImageView> imageViews= new ArrayList<>();

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

        setContentView(R.layout.activity_write_post);
        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        post = (TextView) findViewById(R.id.post);
        edit_post = (EditText) findViewById(R.id.edit_post);
        chose_topic = (Button) findViewById(R.id.chose_topic);
        select_image = (Button) findViewById(R.id.select_image);
        clear_image = (Button) findViewById(R.id.clear_image);
        image_table=(TableLayout)findViewById(R.id.image_table);

        fanhui.setOnClickListener(this);
        chose_topic.setOnClickListener(this);
        select_image.setOnClickListener(this);
        clear_image.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;

        }
    }

    private void showContent(Intent data) {
        /*得到选择的图片的路径*/
        List<String> paths = ImageSelector.getImagePaths(data);
        Log.e("图片路径",paths.toString());
        if (paths.isEmpty()) {
            return;
        }
        /*得到TableLayout中的9个ImageView控件*/
        TableRow childs[]=new TableRow[image_table.getChildCount()];
        for(int i=0;i<childs.length;i++){
            childs[i]=(TableRow) image_table.getChildAt(i);
            for(int j=0;j<childs[i].getChildCount();j++){
                imageViews.add((ImageView) childs[i].getChildAt(j));
            }
        }

        /*赋值*/
        for(int i=0;i<paths.size();i++){
            Glide.with(this).load(paths.get(i)).into(imageViews.get(i));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:

                break;
            case R.id.chose_topic://选择话题

                break;
            case R.id.select_image://选择照片
                ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
                break;
            case R.id.clear_image://重选
                clearImages();
                ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
        }
    }

    /*清空图片表格已经选取的图片，便于重新选取*/
    public void clearImages(){
        for(int i=0;i<imageViews.size();i++){
            imageViews.get(i).setImageDrawable(null);
        }
    }


    private void submit() {
        String post = edit_post.getText().toString().trim();
        if (TextUtils.isEmpty(post)) {
            Toast.makeText(this, "post不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}