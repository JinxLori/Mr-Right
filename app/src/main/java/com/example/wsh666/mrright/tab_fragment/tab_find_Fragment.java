package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;
import com.example.wsh666.mrright.activity.TopicActivity;
import com.example.wsh666.mrright.activity.TopicPostActivity;
import com.example.wsh666.mrright.adapter.MyPagerAdapter;
import com.example.wsh666.mrright.adapter.TopicListAdapter;
import com.example.wsh666.mrright.bean.Topic;
import com.example.wsh666.mrright.util.Get_Data_FromWeb;
import com.example.wsh666.mrright.util.ImageAndAddress;
import com.example.wsh666.mrright.util.String_Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

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
public class Tab_Find_Fragment extends Fragment implements View.OnClickListener,
        ViewPager.OnPageChangeListener {


    private TextView tv_one;
    private TextView tv_two;
    private ImageView img_cursor;
    private ViewPager vpager_four;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离

    TopicListAdapter topicListAdapter;
    Handler mHandler;

    public Tab_Find_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*设置该view中的轮播图内容，必须放在view加载之前*/
        setSlideShowContent();

        View view = inflater.inflate(R.layout.fragment_tab_find_, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        img_cursor = (ImageView) view.findViewById(R.id.img_cursor);
        vpager_four = (ViewPager) view.findViewById(R.id.vpager_four);

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


        //往ViewPager填充View，同时设置点击事件与页面切换事件
        LayoutInflater mInflater = getActivity().getLayoutInflater();
        View view_huati = mInflater.inflate(R.layout.view_huati, null);
        View view_hudong = mInflater.inflate(R.layout.view_two, null);


        /*View_huati界面操作*/
        final List<Topic> topicList = new ArrayList<>();
        final ListView topicListView = (ListView) view_huati.findViewById(R.id.huatituijian);
        GetHuatiRandThread getHuatiRandThread = new GetHuatiRandThread();
        getHuatiRandThread.start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        Topic topic = new Topic();
                        topic = (Topic) msg.getData().getSerializable("msg");
                        topicList.add(topic);

                        topicListAdapter = new TopicListAdapter(topicList, getActivity());
                        topicListView.setAdapter(topicListAdapter);
                        Log.e("gettopicRand", topic.toString());
                        break;
                    default:
                        break;
                }
            }
        };
        /*view_huati界面的控件点击事件*/
        LinearLayout huatiguangchang;
        LinearLayout chuangjianhuati;
        TextView huanyihuan;
        huatiguangchang = (LinearLayout) view_huati.findViewById(R.id.huatiguangchang);
        chuangjianhuati = (LinearLayout) view_huati.findViewById(R.id.chuangjianhuati);
        huanyihuan = (TextView) view_huati.findViewById(R.id.huanyihuan);
        huatiguangchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TopicActivity.class);
                getActivity().startActivity(intent);
            }
        });
        chuangjianhuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "暂不支持创建话题", Toast.LENGTH_SHORT).show();
            }
        });
        huanyihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicList.clear();
                GetHuatiRandThread getTopicRandThread = new GetHuatiRandThread();
                getTopicRandThread.start();
            }
        });
        topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic",topicList.get(i));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), TopicPostActivity.class);
                startActivity(intent);
            }
        });

        /*分类界面的操作*/
        /*加载一个gif图片*/
        WebView webview ;
        webview = (WebView) view_hudong.findViewById(R.id.webview);
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
        webview.loadUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542913231261&di=a48c45b023ba81e355b682a3a800ea7b&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fa79fb5ea0bcee6dd4c0cbb765678341cc70a35972d653-Z60f2V_fw658");
//      往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        listViews.add(view_huati);
        listViews.add(view_hudong);

        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);          //设置ViewPager当前页，从0开始算
        tv_one.setTextColor(getResources().getColor(R.color.green));

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);

        vpager_four.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                vpager_four.setCurrentItem(0);
                break;
            case R.id.tv_two:
                vpager_four.setCurrentItem(1);
                break;
            case R.id.chuangjianhuati:
                break;
            case R.id.huanyihuan:
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
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                }
                break;
            case 1:
                tv_two.setTextColor(getResources().getColor(R.color.green));
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                }
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
    public void chageTextColor() {
        tv_one.setTextColor(getResources().getColor(R.color.black));
        tv_two.setTextColor(getResources().getColor(R.color.black));
    }

    /*设置该view中的轮播图内容，必须放在最前面*/
    public void setSlideShowContent() {
        ArrayList<ImageView> mImageViews;
        mImageViews = new ArrayList<>();
        ImageView view1 = new ImageView(getActivity());
        view1.setBackgroundResource(R.drawable.test);
        ImageView view2 = new ImageView(getActivity());
        view2.setBackgroundResource(R.drawable.add);
        ImageView view3 = new ImageView(getActivity());
        view3.setBackgroundResource(R.drawable.test);
        ImageView view4 = new ImageView(getActivity());
        view4.setBackgroundResource(R.drawable.my_lishi);
        mImageViews.add(view1);
        mImageViews.add(view2);
        mImageViews.add(view3);
        mImageViews.add(view4);
        ImageAndAddress imageAndAddress = ImageAndAddress.getInstance();
        imageAndAddress.setImageViews(mImageViews);
    }

    /*查询话题的线程*/
    public class GetHuatiRandThread extends Thread{
        @Override
        public void run() {
            Get_Data_FromWeb get_data_fromWeb = new Get_Data_FromWeb();
            String jsonData = get_data_fromWeb.getData(String_Util.urlString + "GetTopicRand");
            Gson gson = new Gson();
            List<Topic> topics = gson.fromJson(jsonData,
                    new TypeToken<List<Topic>>() {
                    }.getType());
            if (topics == null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请求数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                for (Topic topic : topics) {
                    Log.e("topic", topic.toString());
                    Message message = new Message();
                    message.what = 1;//判断是哪个handler的请求
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("msg", topic);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }
        }
    }
}
