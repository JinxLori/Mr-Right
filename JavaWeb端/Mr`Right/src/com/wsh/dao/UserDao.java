package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wsh.bean.User;
import com.wsh.db.ConnDB;

public class UserDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	登录
	public User login(User user) {
		User user1=new User();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from tb_user where username=? and password =?";
			ps= conn.prepareStatement(sql);
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getPassword());
			rs=ps.executeQuery();
			if(rs.next()) {
				user1.setUsername(rs.getString(2));
				user1.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return user1;
	}
	
//	注册
	public int register(User user) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql = "insert into tb_user(username,password) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
}
