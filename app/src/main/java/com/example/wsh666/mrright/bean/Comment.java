package com.example.wsh666.mrright.bean;

import java.io.Serializable;

/**
 * Created by wsh666 on 2018/10/12.
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

public class Comment implements Serializable{
    private int comment_id;
    private int post_id;
    private int from_uid;
    private String username;
    private String comment_date;
    private String comment_content;
    private int comment_up_num;
    private int second_comment_num;
    private int comment_level;
    private String is_nice;

    public Comment() {
    }

    public Comment(int comment_id, int post_id, int user_id, String username, String date, int comment_up_num, String comment_content, int second_comment_num, int comment_level,String is_nice) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.from_uid = user_id;
        this.username = username;
        this.comment_date = date;
        this.comment_up_num = comment_up_num;
        this.comment_content = comment_content;
        this.second_comment_num = second_comment_num;
        this.comment_level = comment_level;
        this.is_nice = is_nice;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(int from_uid) {
        this.from_uid = from_uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public int getComment_up_num() {
        return comment_up_num;
    }

    public void setComment_up_num(int comment_up_num) {
        this.comment_up_num = comment_up_num;
    }

    public int getSecond_comment_num() {
        return second_comment_num;
    }

    public void setSecond_comment_num(int second_comment_num) {
        this.second_comment_num = second_comment_num;
    }

    public int getComment_level() {
        return comment_level;
    }

    public void setComment_level(int comment_level) {
        this.comment_level = comment_level;
    }

    public String getIs_nice() {
        return is_nice;
    }

    public void setIs_nice(String is_nice) {
        this.is_nice = is_nice;
    }
}

