package com.wsh.service;

import java.util.List;

import com.wsh.bean.Chat;
import com.wsh.dao.ChatDao;

public class ChatService {
	
	ChatDao chatDao = new ChatDao();
	
//	根据userID查询chat
	public List<Chat> getChatByUserId(int userId){
		return chatDao.getChatByUserId(userId);
	}
//	根据userID查询chat
	public List<Chat> getChat(int sendId,int reciveId){
		return chatDao.getChat(sendId,reciveId);
	}
	
//	根据chatID将信息修改为已读
	public int updateIsRead(int chatId,int chat_recive) {
		return chatDao.updateIsRead(chatId,chat_recive);
	}
	
//	根据发信人和收信人查询未读的chat
	public List<Chat> getChatNotRead(int sendId,int reciveId){
		return chatDao.getChatNotRead(sendId,reciveId);
	}
	
//	添加私信
	public int addChat(int sendId,int reciveId,String chatCotent) {
		return chatDao.addChat(sendId,reciveId,chatCotent);
	}
}
