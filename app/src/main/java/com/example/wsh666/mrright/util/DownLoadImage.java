package com.example.wsh666.mrright.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wsh666 on 2018/11/21.
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

public class DownLoadImage {
    public static void saveImage( String u){
        final String url = u;
        new Thread(){
            @Override
            public void run() {
                Log.e("123","asd");
        /*先转化为Bitmap*/
                URL fileUrl = null;
                Bitmap bitmap = null;
                try {
                    fileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) fileUrl .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        /*在存进本地*/
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    try
                    {
                        File sdcardDir = Environment
                                .getExternalStorageDirectory();
                        /*创建APP图片文件夹*/
                        File imagefile = new File(sdcardDir.getCanonicalPath()
                                + "/DCIM/MrRight");
                        if (!imagefile.exists()) {
                            imagefile.mkdir();
                        }
                        Time t = new Time();
                        t.setToNow();
                        String filename = sdcardDir.getCanonicalPath()
                                + "/DCIM/MrRight"
                                + String.format(
                                "/ReeCam%04d%02d%02d%02d%02d%02d.jpg",
                                t.year, t.month + 1, t.monthDay,
                                t.hour, t.minute, t.second);
                        File file = new File(filename);
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
