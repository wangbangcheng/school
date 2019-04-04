package com.wbc.graduation;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wbc.graduation.util.RecDataUtils;
import com.wbc.graduation.util.RecFileUtils;

@SpringBootApplication
@MapperScan("com.wbc.graduation.dao")
public class RunApplication {
	
	@Autowired
	private static RecFileUtils recFileUtils;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(RunApplication.class, args);
		System.out.println("RunApplication succeed!!!!!");
		// recFileUtils.serverRecSocket();
		/*RecDataUtils recDataUtils = new RecDataUtils();
		recDataUtils.serverRecData();*/
		 
	}

}
