package com.example.wsh666.mrright.bean;

import java.io.Serializable;

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

public class CommentAndPost implements Serializable {
    private int postId;
    private String post_content_text;
    private String comment_content;
    private int from_uid;
    private String comment_date;

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public int getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(int from_uid) {
        this.from_uid = from_uid;
    }

    public String getPost_content_text() {
        return post_content_text;
    }

    public void setPost_content_text(String post_content_text) {
        this.post_content_text = post_content_text;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentAndPost{" +
                "comment_content='" + comment_content + '\'' +
                ", postId=" + postId +
                ", post_content_text='" + post_content_text + '\'' +
                ", from_uid=" + from_uid +
                ", comment_date='" + comment_date + '\'' +
                '}';
    }
}
