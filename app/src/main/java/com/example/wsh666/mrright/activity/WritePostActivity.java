package com.example.wsh666.mrright.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
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
import com.example.wsh666.mrright.util.String_Util;
import com.example.wsh666.mrright.util.UploadImagesUtil;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    private List<String> paths;//选取的图片的本机路径
    private List<ImageView> imageViews= new ArrayList<>();
    private List<String> images = new ArrayList<>();//图片转换为String的集合
    private String photoUrls = "";//得到的服务器图片的地址，
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
        post.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
        }
    }

    private void showContent(Intent data) {
        /*得到选择的图片的路径*/
        paths = ImageSelector.getImagePaths(data);
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

    /*这里不用Hander，因为这里在上传图片并得到返回的服务器图片地址之后，还得进行上传帖子的线程，如果用Hander，会出现异步，也就是还Hander还没有运行，即还没得到地址，上传帖子的线程已经运行*/
    /*通过线程UploadImagesThread得到服务器反馈的图片地址*//*
    private List<String> photoUrls;
    private String photoUrlsString = "" ;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    photoUrls = (List<String>)msg.obj;
                    for(int i=0;i<photoUrls.size();i++){
                        *//*将图片地址集合转换为用，隔开的字符串*//*
                        photoUrlsString = photoUrlsString +photoUrls.get(i)+",";
                        Log.e("WritePostActivity",photoUrlsString);
                    }
                }
                break;
                default:
                    break;
            }
        }
    };*/

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
                break;
            case R.id.post:
                /*上传图片并得到图片在服务器的地址的线程*/
                List<String> imgstrs = imageViewToString();//将图片转换为String
                UploadImagesThread uploadImagesThread = new UploadImagesThread(imgstrs);
                uploadImagesThread.start();
                try {
                    uploadImagesThread.join();//保证图片上传并得到返回的地址之后再运行上传帖子的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*上传帖子的线程*/
                AddPostThread addPostThread = new AddPostThread();
                addPostThread.start();
                break;
        }
    }

    /*清空图片表格已经选取的图片，便于重新选取*/
    public void clearImages(){
        for(int i=0;i<imageViews.size();i++){
            imageViews.get(i).setImageDrawable(null);
        }
    }

    /*发帖（添加帖子的子线程）*/
    private class AddPostThread extends Thread{
        @Override
        public void run() {
            try {
                int userId = String_Util.userId;
                int topicId = 1;//获取话题的id
//               /*这里要将中文当做数据传入URL，需要先对其进行编码，不然传递过去的是乱码*/
                String post_content = URLEncoder.encode(edit_post.getText().toString(),"utf-8");
                Log.e("AddPostThread",photoUrls+" ");
                String path = String_Util.urlString + "AddPost?post_from_id="+userId+"&post_topic_id="+topicId+"&post_content_text="+post_content+"&post_content_image="+photoUrls;
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
                                Toast.makeText(WritePostActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(WritePostActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*上传图片String并得到服务器中图片地址的线程*/
    private class UploadImagesThread extends Thread{
        private List<String> imgStrs;
        public UploadImagesThread(List<String> imgStrs) {
            this.imgStrs = imgStrs;
        }
        @Override
        public void run() {
            for(int i=0;i<images.size();i++){
                /*调用工具类上传图片并得到返回的地址*/
                String upLoadResults = new UploadImagesUtil().upLoadPhoto(imgStrs.get(i));
                photoUrls = photoUrls+upLoadResults+",";//对地址进行拼接
            }
            Log.e("UploadImagesThread","结束");
        }
    }
    /*根据去的图片的路径生成BitMap然后转换为Byte[]并利用Base64将Byte[]加密编码转换成String*/
    public List<String> imageViewToString(){
        for(int i=0;i<paths.size();i++){
            try {
                FileInputStream fis = new FileInputStream(paths.get(i));
                Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//压缩,100表示不压缩
                String imageString = Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);//加密转换成String
                Log.e("WritePostActivity",imageString);
                images.add(imageString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return images;
        /*for(int i=0;i<images.size();i++){
            Log.e("WritePostActivity2",i+"-"+images.get(i));
        }*/
    }
    private void submit() {
        String post = edit_post.getText().toString().trim();
        if (TextUtils.isEmpty(post)) {
            Toast.makeText(this, "post不能为空", Toast.LENGTH_SHORT).show();
        }
    }
}
