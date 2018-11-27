package com.wsh.service;

import java.util.List;

import com.wsh.bean.Remind;
import com.wsh.dao.RemindDao;

public class RemindService {

	RemindDao remindDao = new RemindDao();
	
//	根据userID查询Remind
	public List<Remind> getRemindByUserId(int userId){
		return remindDao.getRemindByUserId(userId);
	}
}
