package com.example.wsh666.mrright.DatebaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wsh666 on 2018/11/22.
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

public class MyHelper extends SQLiteOpenHelper {

    public static String CREATE_TABLE = "create table "+ DatabaseStatic.TABLE_NAME +"(" +
            DatabaseStatic.USER_NAME + " varchar(30), " +
            DatabaseStatic.PASSWORD + " varchar(30), " +
            DatabaseStatic.HEADIMAGE + " varchar(255) "+
            DatabaseStatic.USERID + " real)";// 用于创建表的SQL语句
    private Context myContext = null;

    public MyHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
    }

    public MyHelper(Context context)
    {
        super(context, DatabaseStatic.DATABASE_NAME, null, DatabaseStatic.DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("UseDatabase", "创建数据库");
        Toast.makeText(myContext, "创建数据库", Toast.LENGTH_SHORT).show();
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
