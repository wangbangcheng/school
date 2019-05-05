package com.wbc.graduation.dao;



import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.wbc.graduation.domain.User;


@Repository
@Mapper
public interface UserMapper {
    
	public User selectByPrimaryKey(User user);
	
	public User selectUserByName(User user);
    
    public boolean insertUser(User user);
    
    public boolean updateUser(User user);
    
    public ArrayList<User> selectAll();
    
    public void delete(@Param("arr_id") int[] arr_id);
}