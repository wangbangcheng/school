package com.test.awt;

import java.io.File;
import java.net.URL;

import org.junit.Test;

public class Demo {

	@Test
	public void test() {
		File file = new File(this.getClass().getResource("/").getPath());
		String path = file.getPath();
		path = path.substring(0,path.lastIndexOf("\\")) + "\\classes";
		System.out.println(path);
	}
}
