package com.test.graduation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerTest implements Runnable{
	
	
	
	private static int num = 1;
	 @Override
	    public void run() {
		 
		 
	    }

	    public static void main(String[] args) {
	        try {
	            final ServerSocket server = new ServerSocket(33456);
	            Thread th = new Thread(new Runnable() {

	                @Override
	                public void run() {
	                    while (true) {
	                        try {
	                           System.out.println("开始监听。。。");
	                           Socket socket = server.accept();
	                           System.out.println("有链接");
	                           receiveFile(socket);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    }

	                }

	            });
	            th.run();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    private static void receiveFile(Socket socket) throws IOException {
	        byte[] inputByte = null;
	        int length = 0;
	        DataInputStream dis = null;
	        FileOutputStream fos = null;
	        File file_encrypt = null;
	        String encrypt_title = null;
	        try {
	        	dis = new DataInputStream(socket.getInputStream());
	        	encrypt_title = "E:\\serverTest\\"+dis.readUTF();
	        	file_encrypt = new File(encrypt_title);
	        	
	        	fos = new FileOutputStream(file_encrypt);
	            inputByte = new byte[1024];
	            System.out.println("开始接收数据...");
	            while (true) {
	                if (dis != null) {
	                    length = dis.read(inputByte, 0, inputByte.length);
	                }
	                if (length == -1) {
	                    break;
	                }
	                System.out.println(length);
	                fos.write(inputByte, 0, length);
	                fos.flush();
	            }
	            System.out.println("成功接收加密文件");
	            //des加密 
	           
	          
	            
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        } finally {
	            if (fos != null)
	            	fos.close();
	            if (dis != null)
	            	dis.close();
	            if (socket != null)
	                socket.close();
	           
	            try {
					FileDesTest.decrypt("E:\\serverTest\\123encrypt.jpg", "E:\\serverTest\\111.jpg");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	    private static String createRandomNo(){
	    	String randomNo = null;
	    	//改变输出格式（自己想要的格式） 
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS"); 
	    	//得到字符串时间 
	    	randomNo = formatter.format(new Date()).substring(3,9)+num;
	    	num++;
	    	return randomNo;
	    }

}
