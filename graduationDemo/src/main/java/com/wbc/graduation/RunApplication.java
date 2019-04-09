package com.wbc.graduation;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wbc.graduation.util.RecDataUtils;

@SpringBootApplication
@MapperScan("com.wbc.graduation.dao")
public class RunApplication implements BeanFactoryAware,ApplicationRunner {
	private BeanFactory beanFactory;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(RunApplication.class, args);
		System.out.println("RunApplication succeed!!!!!");
		 
	}

@Override
public void run(ApplicationArguments args) throws Exception {
	RecDataUtils bean = beanFactory.getBean(RecDataUtils.class);
	bean.serverRecData();
}

@Override
public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	this.beanFactory = beanFactory;
}

}
