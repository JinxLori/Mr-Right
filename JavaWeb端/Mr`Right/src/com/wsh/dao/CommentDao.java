package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wsh.bean.Comment;
import com.wsh.db.ConnDB;

public class CommentDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

//	根据post查询下面的评论
	public List<Comment> getCommentByPostId(int post_id,int user_id) {
		List<Comment> comments = new ArrayList<Comment>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from comment_view where post_id = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,post_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt(1));
				comment.setPost_id(rs.getInt(2));
				comment.setComment_content(rs.getString(3));
				comment.setComment_level(rs.getInt(4));
				comment.setFrom_uid(rs.getInt(5));
				comment.setUsername(rs.getString(8));
				comment.setComment_nice_num(rs.getInt(6));
				comment.setComment_date(rs.getString(7));
				
//				得到每一个评论的子评论数
				String sql_find_second_comment_num = "select count(parent_id) from parent_child where parent_id = ?";
				PreparedStatement psStatement = conn.prepareStatement(sql_find_second_comment_num);
				psStatement.setInt(1,rs.getInt(1));
				ResultSet rsResultSet = psStatement.executeQuery();
				while(rsResultSet.next()) {
					comment.setSecond_comment_num(rsResultSet.getInt(1));	
				}
				
//				判断是否已被该用户点赞
				comment.setIs_nice("false");
				List<String> uids = getCommentNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(user_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					comment.setIs_nice("true");
				}
				
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return comments;
	}
	
//	根据comment查询评论
	public List<Comment> getCommentByCommentId(int comment_id,int user_id) {
		List<Comment> comments = new ArrayList<Comment>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from second_comment_view where comment_id in (select child_id from parent_child where parent_id = ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,comment_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt(1));
				comment.setPost_id(rs.getInt(2));
				comment.setComment_content(rs.getString(3));
				comment.setComment_level(rs.getInt(4));
				comment.setFrom_uid(rs.getInt(5));
				comment.setComment_nice_num(rs.getInt(6));
				comment.setComment_date(rs.getString(7));
				comment.setUsername(rs.getString(8));
				
//				判断是否已被该用户点赞
				comment.setIs_nice("false");
				List<String> uids = getCommentNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(user_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					comment.setIs_nice("true");
				}
				
				comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return comments;
	}
	
//	添加评论
	public int addComment(Comment comment) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "insert into comment(post_id,comment_content,comment_level,from_uid) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,comment.getPost_id());
			ps.setString(2,comment.getComment_content());
			ps.setInt(3,comment.getComment_level());
			ps.setInt(4,comment.getFrom_uid());
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
	
//	用户点赞（评论）
	public int addCommentNiceNum(int commentId,int userId) {
		int num = 0;
		String uids = null;
		try {
			conn = ConnDB.openConn();
			String sql_getUid = "SELECT comment_nice_uid FROM `comment` WHERE comment_id = ?";
			PreparedStatement psStatement = conn.prepareStatement(sql_getUid);
			psStatement.setInt(1,commentId);
			ResultSet rsResultSet = psStatement.executeQuery();
			while(rsResultSet.next()) {
				uids = rsResultSet.getString(1);	
			}
			
			String sql = "UPDATE `comment` SET comment_nice_num=comment_nice_num+1 , comment_nice_uid = "+"'"
			+uids+String.valueOf(userId)+","+"'"
			+" WHERE comment_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,commentId);
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
	
//	用户取消点赞(评论)
	public int cancleCommentNiceNum(int commentId,int userId) {
		int num = 0;
		List<String>uids= getCommentNiceUids(commentId);
		String string = String.join(",",(CharSequence[]) uids.toArray())+",";//将List转化为带,的字符串
		String newString = string.replace(String.valueOf(userId)+",", "");//将userId+,转化为空，得到该用户取消赞之后的点赞人的ids
		try {
			conn = ConnDB.openConn();
			String sql = "UPDATE `comment` SET comment_nice_num=comment_nice_num-1 , comment_nice_uid = "+"'"
			+newString+"'"
			+" WHERE comment_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,commentId);
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
	
//	得到点赞的人Id（评论）
	public List<String> getCommentNiceUids(int comment_id) {
		String uids = null;
		List<String> uidList = new ArrayList<String>();
		try {
			conn = ConnDB.openConn();
			String sql = "SELECT comment_nice_uid FROM `comment` WHERE comment_id = ?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);//因为在上面的方法getCommentByPostId中会调用该方法，所以这里的ps,rs不能用全局变量
			ps1.setInt(1,comment_id);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()) {
				uids = rs1.getString(1);
			}
			String []s = uids.split(",");
			uidList = Arrays.asList(s);//String []s转化为List<String>
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
		return uidList;
	}
}
