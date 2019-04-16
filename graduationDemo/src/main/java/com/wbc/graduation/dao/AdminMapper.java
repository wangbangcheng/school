package com.wbc.graduation.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.wbc.graduation.domain.Admin;

@Repository
@Mapper
public interface AdminMapper {

	public Admin checkAdminByName(String name);
	
	public Admin checkAdminByPhone(String phone);
	
	public boolean registerAdmin(Admin admin);
}
