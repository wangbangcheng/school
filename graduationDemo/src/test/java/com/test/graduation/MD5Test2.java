package com.test.graduation;


import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.util.MD5diyUtils;

public class MD5Test2 {
	
    
    public static void main(String []args) throws Md5EncodeException{
    	
        MD5diyUtils md=new MD5diyUtils();
        String message = "123";
        
        String md5_str2 = new MD5diyUtils().encrypt16(message);
        System.out.println("MD5-Algorithm:\nOrigin Message: " + message);
        System.out.println("Result Message: " + md5_str2);
        System.out.println("Message length:"+md5_str2.length());
        System.out.println("Result Message(UpperCase): " + md5_str2.toUpperCase());
        //F0F99260B5A02508C71F6D81C15E9A44
        //3ED9E5F6855DBCDBCD95AC6C4FE0C0A5
    }

}
