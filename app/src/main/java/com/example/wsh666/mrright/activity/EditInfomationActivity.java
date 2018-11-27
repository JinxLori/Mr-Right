package com.example.wsh666.mrright.activity;

import android.app.DatePickerDialog;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.bean.User;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.example.wsh666.mrright.util.UploadImagesUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scrat.app.selectorlibrary.ImageSelector;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created bywsh666 on 2018/11/14 11:09
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
public class EditInfomationActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView fanhui;
    private CircleImageView head_image;
    private EditText edit_name;
    private RadioButton sex_man;
    private RadioButton sex_women;
    private RadioGroup edit_sex;
    private TextView edit_birth;
    private EditText edit_signture;
    private Button update;

    private Handler mHandler;
    User user1 = new User();//查询到的登录用户的信息

    private List<String> path;//选取的图片的本机路径
    private String photoUrl = "";//得到的服务器图片的地址，

    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 1;

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
        setContentView(R.layout.activity_edit_infomation);
        initView();
    }

    private void initView() {
        fanhui = (ImageView) findViewById(R.id.fanhui);
        head_image = (CircleImageView)findViewById(R.id.head_image);
        edit_name = (EditText) findViewById(R.id.edit_name);
        sex_man = (RadioButton) findViewById(R.id.sex_man);
        sex_women = (RadioButton) findViewById(R.id.sex_women);
        edit_sex = (RadioGroup) findViewById(R.id.edit_sex);
        edit_birth = (TextView) findViewById(R.id.edit_birth);
        edit_signture = (EditText) findViewById(R.id.edit_signture);
        update = (Button) findViewById(R.id.update);

        fanhui.setOnClickListener(this);
        head_image.setOnClickListener(this);
        edit_birth.setOnClickListener(this);
        update.setOnClickListener(this);

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
                        break;
                    default:
                        break;
                }
            }
        };
        GetUserByUserIDThread getUserByUserIDThread = new GetUserByUserIDThread();
        getUserByUserIDThread.start();
    }

    @Override
    public void onClick(View v) {
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
            case R.id.head_image:
                ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
                break;
            case R.id.edit_birth:
                showDatePickerDialog();
                break;
            case R.id.update:
                /*上传图片并得到图片在服务器的地址的线程*/
                String imgstr = imageViewToString();//将图片转换为String
                UploadHeadImagesThread uploadHeadImagesThread = new UploadHeadImagesThread(imgstr);
                uploadHeadImagesThread.start();
                try {
                    uploadHeadImagesThread.join();//保证图片上传并得到返回的地址之后再运行上传帖子的线程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UpdateUserThread updateUserThread = new UpdateUserThread();
                updateUserThread.start();
                break;
        }
    }

    /*弹出日期的dialog，并获取选择的日期*/
    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 更新EditText控件日期 小于10前面加0
                edit_birth.setText(new StringBuilder()
                        .append(year)
                        .append(" - ")
                        .append(monthOfYear<10?"0"+(monthOfYear+1):(monthOfYear+1))
                        .append(" - ")
                        .append(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /*输入验证*/
    private void submit() {
        // validate
        String name = edit_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name不能为空", Toast.LENGTH_SHORT).show();
        }

        String signture = edit_signture.getText().toString().trim();
        if (TextUtils.isEmpty(signture)) {
            Toast.makeText(this, "signture不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /*查询信息的线程*/
    private class GetUserByUserIDThread extends Thread {
        @Override
        public void run() {
            Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
            String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetUserByUserId?userid="+ String_Util.userId);
            Gson gson = new Gson();
            List<User> users = gson.fromJson(jsonData,
                    new TypeToken<List<User>>() {}.getType());
            for(User user : users) {
                Log.e("修改信息界面查询用户信息" , user.toString());
                Message message=new Message();
                message.what=1;//判断是哪个handler的请求
                Bundle bundle=new Bundle();
                bundle.putSerializable("msg",user);
                message.setData(bundle);
                mHandler.sendMessage(message);
                /*把查询到的信息显示在界面上*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("user",user1.toString());
                        edit_name.setText(user1.getUsername());
                        Glide.with(EditInfomationActivity.this).load(user1.getHeadimage()).into(head_image);
                        edit_name.setText(user1.getUsername());
                        if(user1.getSex().equals("m")){
                            sex_man.setChecked(true);
                        }else{
                            sex_women.setChecked(true);
                        }
                        edit_birth.setText(user1.getBirthday());
                        edit_signture.setText(user1.getSign());
                    }
                });
            }
        }
    }

    /*修改信息（子线程）*/
    private class UpdateUserThread extends Thread{
        @Override
        public void run() {
            try {
                String userid = String.valueOf(String_Util.userId);
                String username= URLEncoder.encode(edit_name.getText().toString());
                String headimage = photoUrl;
                String sex = "";
                if(sex_man.isChecked()){
                    sex = "m";
                }else{
                    sex = "w";
                }
                String sign = URLEncoder.encode(edit_signture.getText().toString());
                String birthday = URLEncoder.encode(edit_birth.getText().toString());
//               /*这里要将中文当做数据传入URL，需要先对其进行编码，不然传递过去的是乱码*//*
                /*String post_content = URLEncoder.encode(edit_post.getText().toString(),"utf-8");*/
                String path = String_Util.urlString + "UpdateUser?userid="+userid+"&username="+username+"&headimage="+headimage+"&sex="+sex+"&birthday="+birthday+"&sign="+sign;
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
                                Toast.makeText(EditInfomationActivity.this, "修改成功", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(EditInfomationActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            Log.e("进入OnActivityResult","asd");
        }
    }

    private void showContent(Intent data) {
        /*得到选择的图片的路径*/
        path = ImageSelector.getImagePaths(data);
        Log.e("图片路径",path.toString());
        if (path.isEmpty()) {
            Log.e("图片选择为空","asd");
        }
        /*赋值*/
        for(int i=0;i<path.size();i++){
            Glide.with(this).load(path.get(i)).into(head_image);
        }
    }

    /*上传图片String并得到服务器中图片地址的线程*/
    private class UploadHeadImagesThread extends Thread{
        String imgStr;
        public UploadHeadImagesThread(String imgStr) {
            this.imgStr = imgStr;
        }
        @Override
        public void run() {
                /*调用工具类上传图片并得到返回的地址*/
            String upLoadResults = new UploadImagesUtil().upLoadPhoto(imgStr);
            photoUrl = photoUrl+upLoadResults;//对地址进行拼接
            Log.e("UploadHeadImagesThread","结束");
        }
    }
    /*根据去的图片的路径生成BitMap然后转换为Byte[]并利用Base64将Byte[]加密编码转换成String*/
    public String imageViewToString(){
        String imageString = null;
        if(path==null){

        }else{
            for(int i=0;i<path.size();i++) {
                try {
                    FileInputStream fis = new FileInputStream(path.get(i));
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//压缩,100表示不压缩
                    imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);//加密转换成String
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(path.size()==0){

        } else{
            for(int i=0;i<path.size();i++) {
                try {
                    FileInputStream fis = new FileInputStream(path.get(i));
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();//将Bitmap转成Byte[]
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//压缩,100表示不压缩
                    imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);//加密转换成String
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageString;
    }
}
