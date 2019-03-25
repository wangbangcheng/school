package com.test.graduation;

import org.bouncycastle.util.encoders.Base64;

public class Base64Test {
	
	public final static String ENCODEING = "UTF-8";
	
	
	/**
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encode(String data)throws Exception{
		//TODO
		byte[] b = Base64.encode(data.getBytes(ENCODEING));
 		
		
		return new String(b,ENCODEING);
	}
	
	public static String decode(String data) throws Exception{
		byte [] b = Base64.decode(data.getBytes(ENCODEING));
		
		return new String (b,ENCODEING);
	}
	public static void main(String[] args) throws Exception {
		String data = "abc";
		System.out.println(data);
		
		String data_base64 = encode(data);
		System.out.println(data_base64);
		
		System.out.println(decode(data_base64));
		
		
	}
	
	
}
