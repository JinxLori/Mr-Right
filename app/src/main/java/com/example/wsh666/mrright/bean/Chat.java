package com.example.wsh666.mrright.bean;

import java.io.Serializable;

/**
 * Created by wsh666 on 2018/11/17.
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

public class Chat implements Serializable {
    private int chat_id;
    private int chat_send;
    private String send_name;
    private String send_headimage;
    private int chat_recive;
    private String recive_name;
    private String recive_headimage;
    private String chat_content;
    private int is_read;
    private boolean is_meSend;
    private String chat_time;

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getChat_send() {
        return chat_send;
    }

    public void setChat_send(int chat_send) {
        this.chat_send = chat_send;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_headimage() {
        return send_headimage;
    }

    public void setSend_headimage(String send_headimage) {
        this.send_headimage = send_headimage;
    }

    public int getChat_recive() {
        return chat_recive;
    }

    public void setChat_recive(int chat_recive) {
        this.chat_recive = chat_recive;
    }

    public String getRecive_name() {
        return recive_name;
    }

    public void setRecive_name(String recive_name) {
        this.recive_name = recive_name;
    }

    public String getChat_content() {
        return chat_content;
    }

    public void setChat_content(String chat_content) {
        this.chat_content = chat_content;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
    }

    public String getRecive_headimage() {
        return recive_headimage;
    }

    public void setRecive_headimage(String recive_headimage) {
        this.recive_headimage = recive_headimage;
    }

    public boolean is_meSend() {
        return is_meSend;
    }

    public void setIs_meSend(boolean is_meSend) {
        this.is_meSend = is_meSend;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "chat_id=" + chat_id +
                ", chat_send=" + chat_send +
                ", send_name='" + send_name + '\'' +
                ", send_headimage='" + send_headimage + '\'' +
                ", chat_recive=" + chat_recive +
                ", recive_name='" + recive_name + '\'' +
                ", recive_headimage='" + recive_headimage + '\'' +
                ", chat_content='" + chat_content + '\'' +
                ", is_read=" + is_read +
                ", is_meSend=" + is_meSend +
                ", chat_time='" + chat_time + '\'' +
                '}';
    }
}
