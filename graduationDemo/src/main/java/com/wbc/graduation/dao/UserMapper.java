package com.wbc.graduation.dao;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.wbc.graduation.domain.User;


@Repository
@Mapper
public interface UserMapper {
    
	public User selectByPrimaryKey(User user);
	
	public User selectUserByName(String username);
    
    public boolean insertUser(User user);
    
    public boolean updateUser(User user);
}