package com.example.wsh666.mrright.fragment;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wsh666.mrright.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created bywsh666 on 2018/9/17 12:30
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
public class Edit_Information_Fragment extends Fragment implements View.OnClickListener {


    private ImageView fanhui;
    private CircleImageView head_image;
    private EditText edit_name;
    private RadioButton sex_man;
    private RadioButton sex_women;
    private RadioGroup edit_sex;
    private TextView edit_birth;
    private EditText edit_signture;

    public Edit_Information_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_information, container, false);

        initView(view);
        return view;
    }


    private void initView(View view) {
        fanhui = (ImageView) view.findViewById(R.id.fanhui);
        head_image = (CircleImageView) view.findViewById(R.id.head_image);
        edit_name = (EditText) view.findViewById(R.id.edit_name);
        sex_man = (RadioButton) view.findViewById(R.id.sex_man);
        sex_women = (RadioButton) view.findViewById(R.id.sex_women);
        edit_sex = (RadioGroup) view.findViewById(R.id.edit_sex);
        edit_birth = (TextView) view.findViewById(R.id.edit_birth);
        edit_signture = (EditText) view.findViewById(R.id.edit_signture);

        fanhui.setOnClickListener(this);
        head_image.setOnClickListener(this);
        edit_birth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhui:

                break;
            case R.id.head_image:

                break;
            case R.id.edit_birth:
                showDatePickerDialog();
                break;
        }
    }

    /*弹出日期的dialog，并获取选择的日期*/
    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
            Toast.makeText(getActivity(), "name不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String signture = edit_signture.getText().toString().trim();
        if (TextUtils.isEmpty(signture)) {
            Toast.makeText(getActivity(), "signture不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
