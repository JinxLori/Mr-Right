package com.wsh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.wsh.bean.Topic;
import com.wsh.db.ConnDB;

public class TopicDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	随机获取三个话题
	public List<Topic> getTopicRand() {
		List<Topic> topics = new ArrayList<Topic>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from topic order by rand() limit 3";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Topic topic = new Topic();
				topic.setTopic_id(rs.getInt("topic_id"));
				topic.setTopic_content(rs.getString("topic_content"));
				topic.setTopic_image(rs.getString("topic_image"));
				topics.add(topic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return topics;
	}
	
//	获取所有话题
	public List<Topic> getTopic() {
		List<Topic> topics = new ArrayList<Topic>();
		try {
			conn = ConnDB.openConn();
			String sql = "select * from topic";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Topic topic = new Topic();
				topic.setTopic_id(rs.getInt("topic_id"));
				topic.setTopic_content(rs.getString("topic_content"));
				topic.setTopic_image(rs.getString("topic_image"));
				topics.add(topic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return topics;
	}
}
