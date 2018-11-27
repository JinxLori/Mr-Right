package com.wsh.service;

import com.wsh.bean.User;
import com.wsh.dao.UserDao;

public class UserService {
	UserDao userDao=new UserDao();

//	登录
	public int login(String username,String password) {
		return userDao.login(username,password);
	}
	
//	注册
	public int register(User user) {
		return userDao.register(user);
	}
//	根据id得到用户
	public User getUserByUserId(int userId) {
		return userDao.getUserByUserId(userId);
	}
	
//	更新用户信息
	public int updateUser(int userid,User user) {
		return userDao.updateUser(userid,user);
	}
}
