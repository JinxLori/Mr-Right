package com.wsh.bean;

import java.util.Date;

public class Chat {
	private int chat_id;
	private int chat_send;
	private String send_name;
	private String send_headimage;
	private int chat_recive;
	private String recive_name;
	private String recive_headimage;
	private String chat_content;
	private int is_read;
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
	public int getChat_recive() {
		return chat_recive;
	}
	public void setChat_recive(int chat_recive) {
		this.chat_recive = chat_recive;
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
	public String getRecive_name() {
		return recive_name;
	}
	public void setRecive_name(String recive_name) {
		this.recive_name = recive_name;
	}
	public String getRecive_headimage() {
		return recive_headimage;
	}
	public void setRecive_headimage(String recive_headimage) {
		this.recive_headimage = recive_headimage;
	}
	

}
