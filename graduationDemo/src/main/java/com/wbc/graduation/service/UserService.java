package com.wbc.graduation.service;

import com.wbc.graduation.domain.User;


public interface UserService {

	/**
	 * 用户登陆验证
	 */
	public User userLoginCheck(String username);
	
	/**
	 * 用户注册
	 */
	public boolean registerUser(User user);
	
	/**
	 * 通过id查询用户
	 */
	public User selectByPrimaryKey(User user);
}
