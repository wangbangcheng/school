package com.test.graduation;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

public class Demo {

	@Test
	public void test() {
		File file = new File(this.getClass().getResource("/").getPath());
		String path = file.getPath();
		path = path.substring(0,path.lastIndexOf("\\")) + "\\classes";
		System.out.println(path);
	
		System.out.println("Method one:" + Math.random() * 100);
		 
        Random random = new Random();
        System.out.println("Method two:" + random.nextInt(100));

	}
}
