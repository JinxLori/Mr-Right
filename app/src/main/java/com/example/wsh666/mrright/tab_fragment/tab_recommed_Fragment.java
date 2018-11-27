package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;
import com.example.wsh666.mrright.adapter.PostListAdapter;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created bywsh666 on 2018/9/8 13:49
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
public class Tab_Recommed_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener{


    private ViewPager vpager_four;
    private ImageView img_cursor;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;

    TextView test;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离


    Handler mHandler;
    Handler handler1;
    Handler chat_mHandler;

    Socket socket;
    /*帖子列表界面的数据以及ListView，这里需要当做全局变量*/
    List<Post> postList;
    ListView listView ;

    PostListAdapter postListAdapter;


    public Tab_Recommed_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tab_recommed_, container, false);
        /*注册刷新list的广播*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshPostList");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        /*注册刷新单个post点赞数评论数的广播*/
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("action.refreshPost");
        getActivity().registerReceiver(mRefreshBroadcastReceiver1, intentFilter1);

        initViews(view);
        return view;
    }

    private void initViews(final View view) {
        vpager_four = (ViewPager) view.findViewById(R.id.vpager_four);
        vpager_four.setOffscreenPageLimit(6);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.drawable.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one * 2;// 移动两页的偏移量,比如1直接跳3

        /*初始化ViewPager的几个页面*/
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        View view_one=mInflater.inflate(R.layout.view_one,null);
        View view_two=mInflater.inflate(R.layout.view_two,null);
        View view_three=mInflater.inflate(R.layout.view_three,null);

        /*第一个页面的控件处理*/
        postList = new ArrayList<>();
        listView = (ListView) view_one.findViewById(R.id.post_list);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Post post = new Post();
                        post = (Post) msg.getData().getSerializable("msg");
                        postList.add(post);

                        postListAdapter = new PostListAdapter(postList,getActivity());
                        listView.setAdapter(postListAdapter);
                        break;
                    default:
                        break;
                }
            }
        };
//        PostListAdapter postListAdapter = new PostListAdapter(postList,getActivity());
//        listView.setAdapter(postListAdapter);

        /*查询帖子的线程*/
        new Thread(){
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetPostRandServlet?user_id="+ String_Util.userId);
                Gson gson = new Gson();
                List<Post> posts = gson.fromJson(jsonData,
                        new TypeToken<List<Post>>() {}.getType());
                if(posts==null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    for(Post post : posts) {
                        Log.e("Post" , post.toString());
                        Message message=new Message();
                        message.what=1;//判断是哪个handler的请求
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("msg",post);
                        message.setData(bundle);
                        mHandler.sendMessage(message);
                    }
                }
            }
        }.start();

        /*接收服务器主动发送的信息的Hander*//*
        handler1 = new Handler(){
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1){
                    *//*test = (TextView) view.findViewById(R.id.test);
                    test.setText(msg.obj.toString());*//*
                    Log.e("123","这是来自服务器的数据:"+msg.obj.toString());
                }
            }
        };
        *//*接收服务器主动发送信息的线程*//*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.191.1", 30000);
                    // socket.setSoTimeout(10000);//设置10秒超时
                    Log.e("Android", "与服务器建立连接：" + socket);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = br.readLine();
                    Log.e("Android", "与服务器建立连接：" + line);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = line;
                    handler1.sendMessage(msg);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
*/

        /*第二个页面的界面处理,加载一个gif图片*/
        WebView webview ;
        webview = (WebView) view_two.findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        if (Build.VERSION.SDK_INT >= 11) {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 禁止硬件加速
        }
        webview.setBackgroundColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setUseWideViewPort(true); // a
            settings.setLoadWithOverviewMode(true);// b, a和b是成对使用的
        } else {
            settings.setSupportZoom(false);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }
        webview.loadDataWithBaseURL(
                null,
                "<HTML><body bgcolor='#f3f3f3'><div align=center><IMG src='file:///android_asset/ing.gif'/></div></body></html>",
                "text/html", "UTF-8", null);
        webview.loadUrl("https://img.zcool.cn/community/01694f55e1cb2f6ac7251df8dbfc38.gif");

        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        /*LayoutInflater mInflater = getActivity().getLayoutInflater();*/
        listViews.add(view_one);
        //listViews.add(view_two);
        listViews.add(view_three);

        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);//设置ViewPager当前页，从0开始算
        tv_one.setTextColor(getResources().getColor(R.color.green));

        tv_one.setOnClickListener(this);
        /*tv_two.setOnClickListener(this);*/
        tv_three.setOnClickListener(this);

        vpager_four.addOnPageChangeListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                vpager_four.setCurrentItem(0);
                break;
            /*case R.id.tv_two:
                vpager_four.setCurrentItem(1);
                break;*/
            case R.id.tv_three:
                /*vpager_four.setCurrentItem(2);*/
                vpager_four.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageSelected(int index) {
        chageTextColor();
        Animation animation = null;
        switch (index) {
            case 0:
                tv_one.setTextColor(getResources().getColor(R.color.green));
                /*if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else */if (currIndex == 1) {
                    /*animation = new TranslateAnimation(two, 0, 0, 0);*/
                animation = new TranslateAnimation(one, 0, 0, 0);
                }
                break;
           /* case 1:
                tv_two.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;*/
            case 1:
                tv_three.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    /*animation = new TranslateAnimation(offset, two, 0, 0);*/
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } /*else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }*/
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /*更改顶部选项字体颜色*/
    public void chageTextColor(){
        tv_one.setTextColor(getResources().getColor(R.color.small_black));
        /*tv_two.setTextColor(getResources().getColor(R.color.small_black));*/
        tv_three.setTextColor(getResources().getColor(R.color.small_black));
    }

    /*接收发帖界面的广播，进行刷新列表的操作*/
    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshPostList"))
            {
                Log.e("第一个碎片页面接收到发帖之后的广播","yes");
                Post post = new Post();
                post.setUsername(String_Util.username);
                post.setHeadimage(String_Util.userHeadimage);
                post.setPost_content_text(String_Util.post_content_text);
                post.setPost_content_image(String_Util.post_image);
                post.setTopic_content(String_Util.post_topic);
                post.setIs_nice("false");

                postList.add(0,post);
                PostListAdapter postListAdapter = new PostListAdapter(postList,getActivity());
                listView.setAdapter(postListAdapter);
            }
        }
    };

    /*接收帖子详情点赞数评论数更改之后的广播，进行刷新列表的操作*/
    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshPost"))
            {
                Log.e("第一个碎片页面接收到更改信息之后的广播","yes");
                Post post = postList.get(String_Util.post_index);
                post.setPost_nice_num(String_Util.post_nice_num);
                if(String_Util.post_is_nice){
                    post.setIs_nice("true");
                }else {
                    post.setIs_nice("false");
                }
                post.setPost_comment_num(String_Util.post_comment_num);
                postListAdapter.notifyDataSetChanged();
            }
        }
    };

    /*销毁碎片的同时销毁掉注册的广播*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver1);
    }
}
