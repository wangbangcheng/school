package com.wbc.graduation.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbc.graduation.dao.UserMapper;
import com.wbc.graduation.domain.User;
import com.wbc.graduation.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public User userLoginCheck(String username) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUserName(username);
		return userMapper.selectUserByName(user);
	}

	@Override
	public boolean registerUser(User user) {
		return userMapper.insertUser(user);	
	}

	@Override
	public User selectByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
