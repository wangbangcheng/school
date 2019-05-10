package com.wbc.graduation.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SecretKey {

	  public String get() throws FileNotFoundException, IOException{
			Properties pro = new Properties();
			pro.load(new FileInputStream(this.getClass().getResource("/").getPath()+"key.properties"));
			String object = (String) pro.get("secretKey");
			return object;
	  }
}
