package com.wsh.bean;

public class Comment {

	private int comment_id;
	private int post_id;
	private String comment_content;
	private int comment_level;
	private int from_uid;
	private int comment_nice_num;
	private String comment_date;
	private String username;
	private String headimage;
	private int second_comment_num;
	private String is_nice;//数据库中没有的对象
	
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
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getFrom_uid() {
		return from_uid;
	}
	public void setFrom_uid(int from_uid) {
		this.from_uid = from_uid;
	}
	public int getComment_nice_num() {
		return comment_nice_num;
	}
	public void setComment_nice_num(int comment_nice_num) {
		this.comment_nice_num = comment_nice_num;
	}
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	public int getComment_level() {
		return comment_level;
	}
	public void setComment_level(int comment_level) {
		this.comment_level = comment_level;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getSecond_comment_num() {
		return second_comment_num;
	}
	public void setSecond_comment_num(int second_comment_num) {
		this.second_comment_num = second_comment_num;
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
	
	
}
