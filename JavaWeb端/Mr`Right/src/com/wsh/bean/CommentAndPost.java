package com.wsh.bean;

public class CommentAndPost {

	private int postId;
	private String post_content_text;
	private String comment_content;
	private int from_uid;
	private String comment_date;
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPost_content_text() {
		return post_content_text;
	}
	public void setPost_content_text(String post_content_text) {
		this.post_content_text = post_content_text;
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
	public String getComment_date() {
		return comment_date;
	}
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	
	
}
