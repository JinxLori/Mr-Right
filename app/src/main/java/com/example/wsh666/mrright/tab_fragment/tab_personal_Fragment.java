package com.example.wsh666.mrright.tab_fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;

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
public class tab_personal_Fragment extends Fragment implements View.OnClickListener {


    private ImageView image;
    private CollapsingToolbarLayout collapsing_toolbar;
    private AppBarLayout appBar;
    private Toolbar toolbar;
    private FloatingActionButton float_button;

    public tab_personal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_personal_, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        image = (ImageView) view.findViewById(R.id.image);
        collapsing_toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        appBar = (AppBarLayout) view.findViewById(R.id.appBar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        /*toolbar.setOnClickListener(this);*/

        /*显示返回按钮
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar= ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        collapsing_toolbar.setTitle("Test");
        Glide.with(this).load(R.drawable.test).into(image);
        /*悬浮按钮点击*/
        float_button = (FloatingActionButton) view.findViewById(R.id.float_button);
        float_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button:
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
