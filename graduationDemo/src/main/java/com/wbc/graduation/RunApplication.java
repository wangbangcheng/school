package com.wbc.graduation;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wbc.graduation.dao")
public class RunApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 SpringApplication.run(RunApplication.class, args);
		 for(Provider p:Security.getProviders()){
			 System.out.println(p);
			 for(Map.Entry<Object, Object> entry:p.entrySet()){
				 System.out.println("\t"+entry.getKey());
			 }
		 }
		 System.out.println("RunApplication succeed!!!!!");
	}

}
