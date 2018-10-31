package com.example.wsh666.mrright.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wsh666.mrright.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsh666 on 2018/10/31.
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

public class PostGridViewAdapter extends BaseAdapter {
    List<String> imagesAddress = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public PostGridViewAdapter(Context context, List<String> imagesAddress) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.imagesAddress = imagesAddress;
    }

    @Override
    public int getCount() {
        return imagesAddress.size();
    }

    @Override
    public Object getItem(int i) {
        return imagesAddress.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.post_gridview_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        Glide.with(context).load(imagesAddress.get(i)).into(viewHolder.image);
        //viewHolder.image.setImageResource(R.drawable.test);
        return view;
    }

     private class ViewHolder {
         View rootView;
        public ImageView image;

        ViewHolder(View rootView) {
            this.rootView = rootView;
            this.image = (ImageView) rootView.findViewById(R.id.image);
        }
    }
}
