package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.wsh.bean.Post;
import com.wsh.db.ConnDB;

public class PostDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	根据user_id查询post
	public List<Post> getPostByUserId(int post_from_id) {
		List<Post> posts = new ArrayList<Post>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from post_view where post_from_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,post_from_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setPost_from_id(rs.getInt("post_from_id"));
				post.setUsername(rs.getString("username"));
				post.setPost_topic_id(rs.getInt("post_topic_id"));
				post.setTopic_content(rs.getString("topic_content"));
				post.setPost_content_text(rs.getString("post_content_text"));
				post.setPost_content_image(rs.getString("post_content_image"));
				post.setPost_date(rs.getTimestamp("post_date").toString());//getDate()只能得到日期，没有时分秒
				post.setPost_nice_num(rs.getInt("post_nice_num"));
				post.setPost_comment_num(rs.getInt("post_comment_num"));
				post.setHeadimage(rs.getString("headimage"));
				
//				判断是否已被该用户点赞
				post.setIs_nice("false");
				List<String> uids = getPostNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(post_from_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					post.setIs_nice("true");
				}
				posts.add(post);
				System.out.println("~");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(posts.size());
		return posts;
	}
//	添加post
	public int addPost(Post post) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "insert into post(post_from_id,post_topic_id,post_content_text,post_content_image) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, post.getPost_from_id());
			ps.setInt(2, post.getPost_topic_id());
			ps.setString(3,post.getPost_content_text());
			ps.setString(4, post.getPost_content_image());
//			ps.setString(4,post.getPost_date());
//			ps.setInt(5,post.getPost_nice_num());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
//	用户点赞（帖子）
	public int addPostNiceNum(int postId,int userId) {
		int num = 0;
		String uids = null;
		try {
			conn = ConnDB.openConn();
			String sql_getUid = "SELECT post_nice_uids FROM `post` WHERE post_id = ?";
			PreparedStatement psStatement = conn.prepareStatement(sql_getUid);
			psStatement.setInt(1,postId);
			ResultSet rsResultSet = psStatement.executeQuery();
			while(rsResultSet.next()) {
				uids = rsResultSet.getString(1);	
			}
			String sql = "UPDATE `post` SET post_nice_num=post_nice_num+1 , post_nice_uids = "+"'"
			+uids+String.valueOf(userId)+","+"'"
			+" WHERE post_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,postId);
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
//	用户取消点赞(帖子)
	public int canclePostNiceNum(int postId,int userId) {
		int num = 0;
		List<String>uids= getPostNiceUids(postId);
		String string = String.join(",",(CharSequence[]) uids.toArray())+",";//将List转化为带,的字符串
		String newString = string.replace(String.valueOf(userId)+",", "");//将userId+,转化为空，得到该用户取消赞之后的点赞人的ids
		try {
			conn = ConnDB.openConn();
			String sql = "UPDATE `post` SET post_nice_num=post_nice_num-1 , post_nice_uids = "+"'"
			+newString+"'"
			+" WHERE post_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,postId);
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
//	得到点赞的人Id（帖子）
	public List<String> getPostNiceUids(int comment_id) {
		String uids = null;
		Connection conn1 = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<String> uidList = new ArrayList<String>();
		try {
			
			conn1 = ConnDB.openConn();
			String sql = "SELECT post_nice_uids FROM `post` WHERE post_id = ?;";
			ps1 = conn1.prepareStatement(sql);//因为在上面的方法getCommentByPostId中会调用该方法，所以这里的ps,rs不能用全局变量
			ps1.setInt(1,comment_id);
			rs1 = ps1.executeQuery();
			while(rs1.next()) {
				uids = rs1.getString(1);
			}
			String []s = uids.split(",");
			uidList = Arrays.asList(s);//转化为List<String>
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs1, ps1, conn1);
		}
		return uidList;
	}
	
//	根据post_id查询post
	public List<Post> getPostByPostId(int post_id,int user_id) {
		List<Post> posts = new ArrayList<Post>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from post_view where post_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,post_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setPost_from_id(rs.getInt("post_from_id"));
				post.setUsername(rs.getString("username"));
				post.setPost_topic_id(rs.getInt("post_topic_id"));
				post.setTopic_content(rs.getString("topic_content"));
				post.setPost_content_text(rs.getString("post_content_text"));
				post.setPost_content_image(rs.getString("post_content_image"));
				post.setPost_date(rs.getTimestamp("post_date").toString());//getDate()只能得到日期，没有时分秒
				post.setPost_nice_num(rs.getInt("post_nice_num"));
				post.setPost_comment_num(rs.getInt("post_comment_num"));
				post.setHeadimage(rs.getString("headimage"));
				
//				判断是否已被该用户点赞
				post.setIs_nice("false");
				List<String> uids = getPostNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(user_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					post.setIs_nice("true");
				}
				posts.add(post);
				System.out.println("~");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(posts.size());
		return posts;
	}
	
//	随机获取7条数据
	public List<Post> getPostRand(int user_id) {
		List<Post> posts = new ArrayList<Post>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from post_view order by rand() limit 7";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setPost_from_id(rs.getInt("post_from_id"));
				post.setUsername(rs.getString("username"));
				post.setPost_topic_id(rs.getInt("post_topic_id"));
				post.setTopic_content(rs.getString("topic_content"));
				post.setPost_content_text(rs.getString("post_content_text"));
				post.setPost_content_image(rs.getString("post_content_image"));
				post.setPost_date(rs.getTimestamp("post_date").toString());//getDate()只能得到日期，没有时分秒
				post.setPost_nice_num(rs.getInt("post_nice_num"));
				post.setPost_comment_num(rs.getInt("post_comment_num"));
				post.setHeadimage(rs.getString("headimage"));
				
//				判断是否已被该用户点赞
				post.setIs_nice("false");
				List<String> uids = getPostNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(user_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					post.setIs_nice("true");
				}
				posts.add(post);
				System.out.println("~");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(posts.size());
		return posts;
	}
//	根据topic查询Topic
	public List<Post> getPostByTopicID(int topic_id,int user_id) {
		List<Post> posts = new ArrayList<Post>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from post_view where post_topic_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,topic_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setPost_id(rs.getInt("post_id"));
				post.setPost_from_id(rs.getInt("post_from_id"));
				post.setUsername(rs.getString("username"));
				post.setPost_topic_id(rs.getInt("post_topic_id"));
				post.setTopic_content(rs.getString("topic_content"));
				post.setPost_content_text(rs.getString("post_content_text"));
				post.setPost_content_image(rs.getString("post_content_image"));
				post.setPost_date(rs.getTimestamp("post_date").toString());//getDate()只能得到日期，没有时分秒
				post.setPost_nice_num(rs.getInt("post_nice_num"));
				post.setPost_comment_num(rs.getInt("post_comment_num"));
				post.setHeadimage(rs.getString("headimage"));
				
//				判断是否已被该用户点赞
				post.setIs_nice("false");
				List<String> uids = getPostNiceUids(rs.getInt(1));
				int flag = 0;
				for(int i=0;i<uids.size();i++) {
					if(uids.get(i).equals(String.valueOf(user_id))){
						flag=1;
					}
				}
				if(flag == 1) {
					post.setIs_nice("true");
				}
				posts.add(post);
				System.out.println("~");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		System.out.println(posts.size());
		return posts;
	}
}
