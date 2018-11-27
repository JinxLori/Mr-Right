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
    private String headimage;
    private String topic_content;
    private String post_content_image;
    private String post_content_text;
    private String post_date;
    private int zhuanfa_num;
    private int post_nice_num;
    private int post_comment_num;
    private String is_nice;

    public Post() {
    }

    public Post(int post_id, int post_from_id, int post_topic_id, String username, String topic_content, String post_content_image, String post_content_text, String post_date, int zhuanfa_num, int post_nice_num, int post_comment_num, String is_nice) {
        this.post_id = post_id;
        this.post_from_id = post_from_id;
        this.post_topic_id = post_topic_id;
        this.username = username;
        this.topic_content = topic_content;
        this.post_content_image = post_content_image;
        this.post_content_text = post_content_text;
        this.post_date = post_date;
        this.zhuanfa_num = zhuanfa_num;
        this.post_nice_num = post_nice_num;
        this.post_comment_num = post_comment_num;
        this.is_nice = is_nice;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost_content_image() {
        return post_content_image;
    }

    public void setPost_content_image(String post_content_image) {
        this.post_content_image = post_content_image;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
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

    public String getIs_nice() {
        return is_nice;
    }

    public void setIs_nice(String is_nice) {
        this.is_nice = is_nice;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", post_from_id=" + post_from_id +
                ", post_topic_id=" + post_topic_id +
                ", username='" + username + '\'' +
                ", headimage='" + headimage + '\'' +
                ", topic_content='" + topic_content + '\'' +
                ", post_content_image='" + post_content_image + '\'' +
                ", post_content_text='" + post_content_text + '\'' +
                ", post_date='" + post_date + '\'' +
                ", zhuanfa_num=" + zhuanfa_num +
                ", post_nice_num=" + post_nice_num +
                ", post_comment_num=" + post_comment_num +
                ", is_nice='" + is_nice + '\'' +
                '}';
    }
}
