package com.example.wsh666.mrright.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wsh666 on 2018/10/29.
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

public class UploadImagesUtil {

    public static final String url = String_Util.urlString+"GetImageAddressByImgstr";

    public String upLoadPhoto(String imgStr) {
        if(imgStr==null){
            System.out.println("imgStr is null");
        }
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            BasicNameValuePair bnvp = new BasicNameValuePair("param", imgStr);
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            list.add(bnvp);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity2 = response.getEntity();
                String s = EntityUtils.toString(entity2);
                return s;
            }
        } catch (ClientProtocolException e) {

             } catch (IOException e) {

                }
        return null;
        }

}
