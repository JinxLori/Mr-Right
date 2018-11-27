package com.example.wsh666.mrright.DatebaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class DatabaseDao {
    MyHelper myHelper;
    Context context;
    SQLiteDatabase database = null;

    public DatabaseDao(MyHelper myHelper,Context context) {
        this.myHelper = myHelper;
        this.context = context;
    }

    private void createDatabase() // 创建或者打开数据库
    {
        myHelper = new MyHelper(context);
  /*
   * 调用getWritabelDatabase方法或者
   * getReadableDatabase方法时，如果数据库文
   * 件中不存在（注意一个数据库中可以存在多个表格），
   * 那么会回调MyHelper类的onCreate方法新建一个数据库文
   * 件并且在这个数据库文件中新建一
   * 个book表格
   */
        myHelper.getWritableDatabase();
    }


    private void insertDatabase(String username,String password,String headimage) // 向数据库中插入新数据
    {
        if(myHelper == null)
        {
            myHelper = new MyHelper(context);
        }
        database = myHelper.getWritableDatabase();

        ContentValues cV = new ContentValues();
        cV.put(DatabaseStatic.USER_NAME, username);
        cV.put(DatabaseStatic.PASSWORD, password);
        cV.put(DatabaseStatic.HEADIMAGE, headimage);
  /*
   * Android把
   * SQLite的插入语句封装了起来，
   * 通过 ContentValues 类的对象来保存数据库中的数据，
   * 于HashMap
   */
        database.insert(DatabaseStatic.TABLE_NAME, null, cV);

  /*
   * 对应的SQL语句：
   * database.execSQL("insert into " + DatabaseStatic.TABLENAME + " values(?, ?, ?, ?)",
   * new Object[]{"C Language", ++bookSum, "zhidian", 42.6});
   * 或者是这个：
   * database.execSQL("insert into " + DatabaseStatic.TABLENAME + "(" +
   *  DatabaseStatic.BOOKNAME + ", " + DatabaseStatic.ID + ", " +
   *  DatabaseStatic.AUTHOR + ", " + DatabaseStatic.PRICE +
   *  ") values(?, ?, ?, ?)", new Object[]{"C Language", ++bookSum, "zhidian", 42.6});
   * 这里将 ？ 号理解成一个C语言里面的占位符，然后通过 Object[] 数组中的内容补全，下同
   * 参数中的 Object[] 数组是一个通用的数组，里面的数据可以转换为任意类型的数据，通过这个完成不同数据类型变量之间的储存
  */

        Toast.makeText(context, "插入数据成功", Toast.LENGTH_SHORT).show();
    }

    private void searchDatabase() // 查询数据库中的数据
    {
        if(myHelper == null)
        {
            myHelper = new MyHelper(context);
        }
        database = myHelper.getWritableDatabase();
  /*
   * 调用database的query方法，第一个参数是要查询的表名，
   * 后面的参数是一些查询的约束条件，对应于SQL语句的一些参
   * 数， 这里全为null代表查询表格中所有的数据
   * 查询的结果返回一个 Cursor对象
   * 对应的SQL语句：
   * Cursor cursor = database.rawQuery("select * from book", null);
   */
        Cursor cursor = database.query(DatabaseStatic.TABLE_NAME, null, null, null, null, null, null);

        StringBuilder str = new StringBuilder();
        if(cursor.moveToFirst()) // 显示数据库的内容
        {
            for(; !cursor.isAfterLast(); cursor.moveToNext()) // 获取查询游标中的数据
            {
                /*str.append(cursor.getString(cursor.getColumnIndex(DatabaseStatic.USER_NAME)) + " ");
                String_Util.username = cursor.getString(cursor.getColumnIndex(DatabaseStatic.USER_NAME));
                str.append(cursor.getString(cursor.getColumnIndex(DatabaseStatic.PASSWORD)) + " ");
                String_Util.
                str.append(cursor.getString(cursor.getColumnIndex(DatabaseStatic.HEADIMAGE)) + " ");*/
            }
        }
        cursor.close(); // 记得关闭游标对象
        /*if(str.toString().equals(""))
        {
            str.append("数据库为空！");
            textView.setTextColor(Color.RED);
        }
        else
        {
            textView.setTextColor(Color.BLACK);
        }
        textView.setText(str.toString());*/
    }
}
