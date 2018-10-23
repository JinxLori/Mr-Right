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

public class Post implements Serializable {
    private int post_id;
    private int post_from_id;
    private int post_topic_id;
    private String username;
    private String topic_content;
    private String post_content_text;
    private String post_date;
    private int zhuanfa_num;
    private int post_nice_num;
    private int post_comment_num;

    public Post() {
    }

    public Post(int post_id, int post_from_id, int post_topic_id, String user_name, String post_topic, String post_content_text, String post_date, int zhuanfa_num, int post_nice_num, int post_comment_num) {
        this.post_id = post_id;
        this.post_from_id = post_from_id;
        this.post_topic_id = post_topic_id;
        this.username = user_name;
        this.topic_content = post_topic;
        this.post_content_text = post_content_text;
        this.post_date = post_date;
        this.zhuanfa_num = zhuanfa_num;
        this.post_nice_num = post_nice_num;
        this.post_comment_num = post_comment_num;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getPost_from_id() {
        return post_from_id;
    }

    public void setPost_from_id(int post_from_id) {
        this.post_from_id = post_from_id;
    }

    public int getPost_topic_id() {
        return post_topic_id;
    }

    public void setPost_topic_id(int post_topic_id) {
        this.post_topic_id = post_topic_id;
    }

    public String getUser_name() {
        return username;
    }

    public void setUser_name(String user_name) {
        this.username = user_name;
    }

    public String getPost_topic() {
        return topic_content;
    }

    public void setPost_topic(String post_topic) {
        this.topic_content = post_topic;
    }

    public String getPost_content_text() {
        return post_content_text;
    }

    public void setPost_content_text(String post_content_text) {
        this.post_content_text = post_content_text;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public int getZhuanfa_num() {
        return zhuanfa_num;
    }

    public void setZhuanfa_num(int zhuanfa_num) {
        this.zhuanfa_num = zhuanfa_num;
    }


    public int getPost_nice_num() {
        return post_nice_num;
    }

    public void setPost_nice_num(int post_nice_num) {
        this.post_nice_num = post_nice_num;
    }

    public int getPost_comment_num() {
        return post_comment_num;
    }

    public void setPost_comment_num(int post_comment_num) {
        this.post_comment_num = post_comment_num;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", post_from_id=" + post_from_id +
                ", post_topic_id=" + post_topic_id +
                ", user_name='" + username + '\'' +
                ", post_topic='" + topic_content + '\'' +
                ", post_content_text='" + post_content_text + '\'' +
                ", post_date='" + post_date + '\'' +
                ", zhuanfa_num=" + zhuanfa_num +
                ", post_comment_num=" + post_comment_num +
                ", post_nice_num=" + post_nice_num +
                '}';
    }

    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(post_id);
        parcel.writeInt(post_from_id);
        parcel.writeInt(post_topic_id);
        parcel.writeString(username);
        parcel.writeString(topic_content);
        parcel.writeString(post_date);
        parcel.writeInt(pinglun_num);
        parcel.writeInt(post_nice_num);
        parcel.writeInt(post_comment_num);
    }*/
}
