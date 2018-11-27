package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wsh.bean.Chat;
import com.wsh.bean.Post;
import com.wsh.db.ConnDB;


public class ChatDao {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	根据接收的用户id查询chat
	public List<Chat> getChatByUserId(int userId){
		List<Chat> chats = new ArrayList<Chat>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from chat_view where chat_recive = ? and is_read=0";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChat_id(rs.getInt("chat_id"));
				chat.setChat_send(rs.getInt("chat_send"));
				chat.setChat_recive(rs.getInt("chat_recive"));
				chat.setSend_name(rs.getString("send_name"));
				chat.setSend_headimage(rs.getString("send_headimage"));
				chat.setRecive_name(rs.getString("recive_name"));
				chat.setRecive_headimage(rs.getString("recive_headimage"));
				chat.setChat_content(rs.getString("chat_content"));
				chat.setIs_read(rs.getInt("is_read"));
				chat.setChat_time(rs.getTimestamp("chat_time").toString());
				chats.add(chat);
				}
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(chats.size());
		return chats;
	}
	
//	根据发信人和收信人查询已读的chat
	public List<Chat> getChat(int sendId,int reciveId){
		List<Chat> chats = new ArrayList<Chat>();
		try {
			conn = ConnDB.openConn();
			String sql = "SELECT * from chat_view WHERE chat_send IN(?,?) AND chat_recive IN(?,?);";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,sendId);
			ps.setLong(2,reciveId);
			ps.setLong(3,sendId);
			ps.setLong(4,reciveId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChat_id(rs.getInt("chat_id"));
				chat.setChat_send(rs.getInt("chat_send"));
				chat.setChat_recive(rs.getInt("chat_recive"));
				chat.setSend_name(rs.getString("send_name"));
				chat.setSend_headimage(rs.getString("send_headimage"));
				chat.setRecive_name(rs.getString("recive_name"));
				chat.setRecive_headimage(rs.getString("recive_headimage"));
				chat.setChat_content(rs.getString("chat_content"));
				chat.setIs_read(rs.getInt("is_read"));
				chat.setChat_time(rs.getTimestamp("chat_time").toString());
				chats.add(chat);
				}
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(chats.size());
		return chats;
	}
	
//	根据chatID将信息修改为已读
	public int updateIsRead(int chatId,int chat_recive) {
		int n = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "UPDATE `chat` SET is_read=1 where chat_id=? and chat_recive = ?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,chatId);
			ps.setLong(2,chat_recive);
			n= ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		
		return n;
	}
	
//	根据发信人和收信人查询未读的chat
	public List<Chat> getChatNotRead(int sendId,int reciveId){
		List<Chat> chats = new ArrayList<Chat>();
		try {
			conn = ConnDB.openConn();
			String sql = "SELECT * from chat_view WHERE chat_send =? AND chat_recive =? and is_read=0;";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,sendId);
			ps.setLong(2,reciveId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Chat chat = new Chat();
				chat.setChat_id(rs.getInt("chat_id"));
				chat.setChat_send(rs.getInt("chat_send"));
				chat.setChat_recive(rs.getInt("chat_recive"));
				chat.setSend_name(rs.getString("send_name"));
				chat.setSend_headimage(rs.getString("send_headimage"));
				chat.setRecive_name(rs.getString("recive_name"));
				chat.setRecive_headimage(rs.getString("recive_headimage"));
				chat.setChat_content(rs.getString("chat_content"));
				chat.setIs_read(rs.getInt("is_read"));
				chat.setChat_time(rs.getTimestamp("chat_time").toString());
				chats.add(chat);
				}
			}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(chats.size());
		return chats;
	}
	
//	添加私信
	public int addChat(int sendId,int reciveId,String chatCotent) {
		int n = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "insert into chat(chat_send,chat_recive,chat_content) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, sendId);
			ps.setInt(2, reciveId);
			ps.setString(3, chatCotent);
			n = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return n;
		
	}
}
