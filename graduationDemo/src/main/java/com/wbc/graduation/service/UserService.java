package com.wbc.graduation.service;

import java.util.ArrayList;

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
	 
	/**
	 * 查询所有用户
	 */
	public ArrayList<User> selectAllUser();
	
	/**
	 * 删除用户
	 */
	public void delete(int[] arr_id);
}
