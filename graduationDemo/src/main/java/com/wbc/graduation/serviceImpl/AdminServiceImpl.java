package com.wbc.graduation.serviceImpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbc.graduation.dao.AdminMapper;
import com.wbc.graduation.domain.Admin;
import com.wbc.graduation.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminMapper adminMapper;
	
	
	@Override
	public Admin checkAdmin(String name) {
		Admin admin_1 = adminMapper.checkAdminByName(name);
		if(admin_1 == null) {
			return adminMapper.checkAdminByPhone(name);
		}
		return admin_1;
	}
		
		

	@Override
	public boolean registerAdmin(Admin admin) {
		if(admin.getName() == null) {
			String name = "管理员"+ getRandom(8);
			admin.setName(name);
		}
		return adminMapper.registerAdmin(admin);
	}

	
	private String getRandom(int strLength) {  
	      
	    Random rm = new Random();  
	      
	    // 获得随机数  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	  
	    // 将获得的获得随机数转化为字符串  
	    String fixLenthString = String.valueOf(pross);  
	  
	    // 返回固定的长度的随机数  
	    return fixLenthString.substring(1, strLength + 1);  
	}  
}
