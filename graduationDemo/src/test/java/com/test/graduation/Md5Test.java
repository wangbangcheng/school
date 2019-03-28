package com.test.graduation;

import java.security.MessageDigest;

import org.springframework.util.DigestUtils;



public class Md5Test {

	
	public final static String md5Encode(String str) throws Exception{
		String newstr = DigestUtils.md5DigestAsHex(str.getBytes());
		return newstr;
	}
	public static void main(String[] args) {
		String data = "123";
	
		
		try {
			System.out.println(md5Encode(data));
			System.out.println(61/64);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
