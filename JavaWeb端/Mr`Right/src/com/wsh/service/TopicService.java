package com.wsh.service;

import java.util.List;

import com.wsh.bean.Topic;
import com.wsh.dao.TopicDao;

public class TopicService {

	TopicDao topicDao = new TopicDao();
	
//	随机获取三个话题
	public List<Topic> getTopicRand(){
		return topicDao.getTopicRand();
	}
	
//	获取所有话题
	public List<Topic> getTopic(){
		return topicDao.getTopic();
	}
}
