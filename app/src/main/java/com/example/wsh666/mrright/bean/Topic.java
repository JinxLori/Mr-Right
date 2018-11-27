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

public class Topic implements Serializable {
    private int topic_id;
    private String topic_content;
    private String topic_image;

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_image() {
        return topic_image;
    }

    public void setTopic_image(String topic_image) {
        this.topic_image = topic_image;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topic_content='" + topic_content + '\'' +
                ", topic_id=" + topic_id +
                ", topic_image='" + topic_image + '\'' +
                '}';
    }
}
