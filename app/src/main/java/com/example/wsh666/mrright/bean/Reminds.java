package com.example.wsh666.mrright.bean;

import java.io.Serializable;

/**
 * Created by wsh666 on 2018/11/18.
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

public class Reminds implements Serializable {
    private int remind_id;
    private String remind_from_name;
    private String remind_content;
    private int remind_to;
    private int remind_reason_type;
    private int remind_reason;
    private String remind_reason_content;
    private int is_read;

    public int getRemind_id() {
        return remind_id;
    }

    public void setRemind_id(int remind_id) {
        this.remind_id = remind_id;
    }

    public String getRemind_from_name() {
        return remind_from_name;
    }

    public void setRemind_from_name(String remind_from_name) {
        this.remind_from_name = remind_from_name;
    }

    public String getRemind_content() {
        return remind_content;
    }

    public void setRemind_content(String remind_content) {
        this.remind_content = remind_content;
    }

    public int getRemind_reason_type() {
        return remind_reason_type;
    }

    public void setRemind_reason_type(int remind_reason_type) {
        this.remind_reason_type = remind_reason_type;
    }

    public int getRemind_to() {
        return remind_to;
    }

    public void setRemind_to(int remind_to) {
        this.remind_to = remind_to;
    }

    public int getRemind_reason() {
        return remind_reason;
    }

    public void setRemind_reason(int remind_reason) {
        this.remind_reason = remind_reason;
    }

    public String getRemind_reason_content() {
        return remind_reason_content;
    }

    public void setRemind_reason_content(String remind_reason_content) {
        this.remind_reason_content = remind_reason_content;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }


    @Override
    public String toString() {
        return "Reminds{" +
                "remind_id=" + remind_id +
                ", remind_from_name='" + remind_from_name + '\'' +
                ", remind_content='" + remind_content + '\'' +
                ", remind_to=" + remind_to +
                ", remind_reason_type=" + remind_reason_type +
                ", remind_reason=" + remind_reason +
                ", remind_reason_content='" + remind_reason_content + '\'' +
                ", is_read=" + is_read +
                '}';
    }
}
