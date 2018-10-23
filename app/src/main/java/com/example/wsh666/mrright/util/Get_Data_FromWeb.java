package com.example.wsh666.mrright.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wsh666 on 2018/10/22.
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

public class Get_Data_FromWeb {
    /*从web页面获取json字符串*/
    public String getData(String web) {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;
        stringBuffer = new StringBuffer();
        try {
            URL url = new URL(web);//json地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//使用get方法接收
            InputStream inputStream = connection.getInputStream();//得到一个输入流
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTf-8"));
            String sread = null;
            while ((sread = bufferedReader.readLine()) != null) {
                stringBuffer.append(sread);
                stringBuffer.append("\r\n");
            }
            Log.e("Get_Data_FromWeb", stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
