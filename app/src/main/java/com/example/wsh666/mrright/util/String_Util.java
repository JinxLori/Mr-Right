package com.example.wsh666.mrright.util;

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

public class String_Util {
    public static  String urlString = "http://192.168.191.1:8080/Mr_Right/";

    public static  int userId = 1;
    public static String userHeadimage = "";
    public static String username = "";

    /*发帖成功之后，需要利用广播刷新PostList，所以将发帖的信息存为全局静态变量*/
    public static String post_topic = "";
    public static String post_content_text= "";
    public static String post_image="";

    /*在帖子详情界面如果对点赞或者评论进行了操作，需要在PostList界面刷新对应的帖子的点赞和评论数信息*/
    public static int post_index = 0;
    public static int post_nice_num=0;
    public static int post_comment_num=0;
    public static boolean post_is_nice=false;

    /*在评论详情界面如果对点赞或者评论进行了操作，需要在CommentList界面刷新对应的评论的显示信息*/
    public static int comment_index = 0;
    public static int comment_nice_num = 0;
    public static int added_comment_comment_num = 0;
    public static boolean comment_is_nice = false;
}
