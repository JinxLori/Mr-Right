package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sun.crypto.provider.RSACipher;
import com.wsh.bean.User;
import com.wsh.db.ConnDB;

public class UserDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	登录
	public int login(String username,String password) {
		int n=0;
		try {
			conn = ConnDB.openConn();
			String sql = "select userid from tb_user where username=? and password =?";
			ps= conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,password);
			rs=ps.executeQuery();
			if(rs.next()) {
				n=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return n;
	}
	
//	注册
	public int register(User user) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "insert into tb_user(username,password,headimage,phonenumber,sex,birthday,sign) values(?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getHeadimage());
			ps.setString(4, user.getPhonenumber());
			ps.setString(5, user.getSex());
			ps.setString(6, user.getBirthday());
			ps.setString(7, user.getSign());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
	
//	根据id得到用户
	public User getUserByUserId(int userId) {
		User user = new User();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from tb_user where userid=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1,userId);
			rs=ps.executeQuery();
			if(rs.next()) {
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setHeadimage(rs.getString(4));
				user.setPhonenumber(rs.getString(5));
				user.setSex(rs.getString(6));
				user.setBirthday(rs.getString(7));
				user.setSign(rs.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return user;
	}
	
//	更新用户信息
	public int updateUser(int userid,User user) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "UPDATE `tb_user` SET username = ? ,headimage= ?,sex = ?,birthday = ?,sign = ?  WHERE userid = ?";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getHeadimage());
			ps.setString(3,user.getSex());
			ps.setString(4,user.getBirthday());
			ps.setString(5,user.getSign());
			ps.setInt(6,userid);
			num = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
}
