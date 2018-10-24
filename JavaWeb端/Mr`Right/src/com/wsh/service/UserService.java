package com.wsh.service;

import com.wsh.bean.User;
import com.wsh.dao.UserDao;

public class UserService {
	UserDao userDao=new UserDao();

//	登录
	public User login(User user) {
		return userDao.login(user);
	}
	
//	注册
	public int register(User user) {
		return userDao.register(user);
	}
	
}
