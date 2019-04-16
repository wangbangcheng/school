package com.wbc.graduation.service;

import org.springframework.stereotype.Service;

import com.wbc.graduation.domain.Admin;

@Service
public interface AdminService {

	public Admin checkAdmin(String name);
	
	public boolean registerAdmin(Admin admin);
}
