package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.wsh.bean.Remind;
import com.wsh.db.ConnDB;

public class RemindDao {
	

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

//	根据用户id获取提醒内容
	public List<Remind> getRemindByUserId(int userId){
		List<Remind> remindList = new ArrayList<Remind>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from remind where remind_to = ?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1,userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				Remind remind = new Remind();
				remind.setRemind_from_name(rs.getString("remind_from_name"));
				remind.setRemind_to(rs.getInt("remind_to"));
				remind.setRemind_content(rs.getString("remind_content"));
				remind.setRemind_reason(rs.getInt("remind_reason"));
				remind.setRemind_reason_type(rs.getInt("remind_reason_type"));
				remind.setRemind_reason_content(rs.getString("remind_reason_content"));
				remind.setIs_read(rs.getInt("is_read"));
				remindList.add(remind);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return remindList;
	}
}
