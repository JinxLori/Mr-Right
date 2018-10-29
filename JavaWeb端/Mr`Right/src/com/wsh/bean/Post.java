package com.wsh.bean;

import java.sql.Blob;

public class Post {

	private int post_id;
	private int post_from_id; 
	private String username;
	private int post_topic_id;
	private String topic_content;
	private String post_content_image;
	private String post_content_text;
	private String post_date;
	private int post_nice_num;
	private int post_comment_num;
	private String is_nice;//数据库中没有的对象
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPost_topic_id() {
		return post_topic_id;
	}
	public void setPost_topic_id(int post_topic_id) {
		this.post_topic_id = post_topic_id;
	}
	public String getTopic_content() {
		return topic_content;
	}
	public void setTopic_content(String topic_content) {
		this.topic_content = topic_content;
	}
	public String getPost_content_image() {
		return post_content_image;
	}
	public void setPost_content_image( String blob) {
		this.post_content_image = blob;
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
	
	
	
	
}
