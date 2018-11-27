package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.EditInfomationActivity;
import com.example.wsh666.mrright.activity.LoginActivity;
import com.example.wsh666.mrright.adapter.MyCommentListAdapter;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;
import com.example.wsh666.mrright.adapter.PostListAdapter;
import com.example.wsh666.mrright.bean.CommentAndPost;
import com.example.wsh666.mrright.bean.Post;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created bywsh666 on 2018/9/10 13:48
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
public class Tab_Personal_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener{


    private ImageView image;
    private CollapsingToolbarLayout collapsing_toolbar;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private FloatingActionButton float_button;
    private CircleImageView personal_head_image;
    private TextView huozan_num;
    private LinearLayout huozan;
    private TextView guanzhu_num;
    private LinearLayout guanzhu;
    private TextView fensi_num;
    private LinearLayout fensi;
    private TextView tiezi;
    private TextView genpai;
    private TextView pinglun;
    private TextView huati;
    private ImageView img_cursor;
    private ViewPager vpager_personal;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int bmpHeight;
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离
    private int three=0;

    Handler mHandler;

    List<Post> postList;
    ListView listView ;

    PostListAdapter postListAdapter;

    Handler handler;

    public Tab_Personal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_personal_, container, false);
        initView(view);

        /*注册登录界面的广播*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshUser");
        getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        return view;
    }

    private void initView(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) view.findViewById(R.id.appBar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        /*头像*/
        personal_head_image = (CircleImageView) view.findViewById(R.id.personal_head_image);
        /*personal_head_image.setImageResource(R.drawable.test);*/
        personal_head_image.setOnClickListener(this);

        if(String_Util.userId!=0){
            collapsing_toolbar.setTitle(String_Util.username);
            Glide.with(this).load(String_Util.userHeadimage).into(image);
            Glide.with(this).load(String_Util.userHeadimage).into(personal_head_image);
        }else{
            collapsing_toolbar.setTitle("未登录");
            Glide.with(this).load(R.drawable.test).into(image);
            Glide.with(this).load(R.drawable.test).into(personal_head_image);
        }

        /*悬浮按钮点击*/
        float_button = (FloatingActionButton) view.findViewById(R.id.float_button);
        float_button.setOnClickListener(this);

        /*huozan_num = (TextView) view.findViewById(R.id.huozan_num);
        huozan_num.setText("6");
        huozan = (LinearLayout) view.findViewById(R.id.huozan);
        huozan.setOnClickListener(this);
        guanzhu_num = (TextView) view.findViewById(R.id.guanzhu_num);
        guanzhu_num.setText("2");
        guanzhu = (LinearLayout) view.findViewById(R.id.guanzhu);
        guanzhu.setOnClickListener(this);
        fensi_num = (TextView) view.findViewById(R.id.fensi_num);
        fensi_num.setText("3");
        fensi = (LinearLayout) view.findViewById(R.id.fensi);
        fensi.setOnClickListener(this);*/


        tiezi = (TextView) view.findViewById(R.id.tiezi);
        tiezi.setOnClickListener(this);
        genpai = (TextView) view.findViewById(R.id.genpai);
        genpai.setOnClickListener(this);
        pinglun = (TextView) view.findViewById(R.id.pinglun);
        pinglun.setOnClickListener(this);
        /*huati = (TextView) view.findViewById(R.id.huati);*/
        /*huati.setOnClickListener(this);*/
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);
        img_cursor.setOnClickListener(this);
        vpager_personal = (ViewPager) view.findViewById(R.id.vpager_personal);
        vpager_personal.setOnClickListener(this);


        //下划线动画的相关设置：
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.line);
        bmpWidth = bitmap.getWidth();// 获取图片宽度
        bmpHeight = bitmap.getHeight();
        Log.e("1",String.valueOf(bmpWidth));
        int width=50;
        int hight=8;
        float scaleWidth=((float)width)/bmpWidth;
        float scaleHeight=((float)hight)/bmpHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        bitmap=Bitmap.createBitmap(bitmap,0,0,bmpWidth,bmpHeight,matrix,true);
        img_cursor.setImageBitmap(bitmap);
        bmpWidth=bitmap.getWidth();
        Log.e("2",String.valueOf(bmpWidth));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        Log.e("screen",String.valueOf(screenW));
        offset = (screenW / 3 - bmpWidth) / 2;// 计算偏移量
        Log.e("offset",String.valueOf(offset));
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one* 2 ;
        three=one*3;

        /*初始化ViewPager的几个页面*/
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        View view_one=mInflater.inflate(R.layout.view_one,null);
        View view_two=mInflater.inflate(R.layout.view_two,null);
        View view_three=mInflater.inflate(R.layout.view_three,null);
        View view_four=mInflater.inflate(R.layout.view_one,null);

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
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetPostByUserIdServlet?user_id="+ String_Util.userId);
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

        /*我的评论页面*/
        final List<CommentAndPost> commentAndPostList = new ArrayList<>();
        final MyCommentListAdapter myCommentListAdapter = new MyCommentListAdapter(commentAndPostList,getActivity());
        final ListView pinglunListView = (ListView) view_three.findViewById(R.id.mypinglun);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        /*Post post = new Post();
                        post = (Post) msg.getData().getSerializable("msg");
                        postList.add(post);*/

                        /*postListAdapter = new PostListAdapter(postList,getActivity());
                        listView.setAdapter(postListAdapter);*/
                        CommentAndPost commentAndPost = new CommentAndPost();
                        commentAndPost = (CommentAndPost) msg.getData().getSerializable("msg");
                        commentAndPostList.add(commentAndPost);

                        pinglunListView.setAdapter(myCommentListAdapter);
                        Log.e("我的评论",commentAndPost.toString());
                        break;
                    default:
                        break;
                }
            }
        };
//        PostListAdapter postListAdapter = new PostListAdapter(postList,getActivity());
//        listView.setAdapter(postListAdapter);

        /*查询评论的线程*/
        new Thread(){
            @Override
            public void run() {
                Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
                String jsonData = get_data_fromWeb.getData(String_Util.urlString+"GetCommentByUserId?user_id="+ String_Util.userId);
                Gson gson = new Gson();
                List<CommentAndPost> commentAndPosts = gson.fromJson(jsonData,
                        new TypeToken<List<CommentAndPost>>() {}.getType());
                if(commentAndPosts==null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    for(CommentAndPost commentAndPost : commentAndPosts) {
                        Log.e("CommentAndPost" , commentAndPost.toString());
                        Message message=new Message();
                        message.what=1;//判断是哪个handler的请求
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("msg",commentAndPost);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }
            }
        }.start();

        /*我的跟拍界面*/
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
        webview.loadUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542913472436&di=b186814cb7ee8d490fe4be4051e12538&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01197b5a3b8225a80121db80826655.gif");
        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        /*LayoutInflater mInflater = getActivity().getLayoutInflater();*/
        listViews.add(view_one);
        listViews.add(view_two);
        listViews.add(view_three);
        //listViews.add(view_four);

        vpager_personal.setAdapter(new MyPagerAdapter(listViews));
        vpager_personal.setCurrentItem(0);//设置ViewPager当前页，从0开始算
        tiezi.setTextColor(getResources().getColor(R.color.green));

        vpager_personal.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.float_button:
                /*Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();*/
                /*editInformationFragment = new Edit_Information_Fragment();
                ((MainActivity) getActivity()).replaceFragment(editInformationFragment, this);*/
                intent = new Intent();
                intent.setClass(getActivity(), EditInfomationActivity.class);
                startActivity(intent);
                break;
            case R.id.personal_head_image:
                //Toast.makeText(getActivity(), "头像", Toast.LENGTH_SHORT).show();
                intent=new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            /*case R.id.huozan:

                break;
            case R.id.guanzhu:

                break;
            case R.id.fensi:

                break;*/
            case R.id.tiezi:
                vpager_personal.setCurrentItem(0);
                break;
            case R.id.genpai:
                vpager_personal.setCurrentItem(1);
                break;
            case R.id.pinglun:
                vpager_personal.setCurrentItem(2);
                break;
            /*case R.id.huati:
                vpager_personal.setCurrentItem(3);
                break;*/
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int index) {

        chageTextColor();
        Animation animation = null;
        switch (index) {
            case 0:
                tiezi.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }/* else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, 0, 0, 0);
                }*/
                break;
            case 1:
                genpai.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                } /*else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, one, 0, 0);
                }*/
                break;
            case 2:
                pinglun.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                } /*else if (currIndex == 3) {
                    animation = new TranslateAnimation(three, two, 0, 0);
                }*/
                break;
            /*case 3:
                huati.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, three, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, three, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, three, 0, 0);
                }
                break;*/
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*更改顶部选项字体颜色*/
    public void chageTextColor(){
        tiezi.setTextColor(getResources().getColor(R.color.black));
        genpai.setTextColor(getResources().getColor(R.color.black));
        pinglun.setTextColor(getResources().getColor(R.color.black));
        /*huati.setTextColor(getResources().getColor(R.color.black));*/
    }

    /*接收登陆界面的广播*/
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshUser"))
            {
                Log.e("第四个碎片页面接收到登陆之后的广播","yes");
                Glide.with(getActivity()).load(String_Util.userHeadimage).into(personal_head_image);
                Glide.with(getActivity()).load(String_Util.userHeadimage).into(image);
                collapsing_toolbar.setTitle(String_Util.username);
            }
        }
    };

    /*销毁碎片的同时销毁掉注册的广播*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mRefreshBroadcastReceiver);
    }
}
