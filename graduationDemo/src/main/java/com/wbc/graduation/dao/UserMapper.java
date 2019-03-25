package com.wbc.graduation.dao;



import org.springframework.stereotype.Repository;
import com.wbc.graduation.domain.User;


@Repository
public interface UserMapper {
    
	public User selectByPrimaryKey(User user);
	
	public User selectUserByName(String username);
    
    public boolean insertUser(User user);
    
    public boolean updateUser(User user);
}