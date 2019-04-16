package com.wbc.graduation.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;



public class RecFileUtils implements Runnable{

	private String pre_Recpath = getPath();
	private static int num = 1;
	 @Override
	    public void run() {
		 
		 
	    }

		private String getPath() {
			File file = new File(this.getClass().getResource("/").getPath());
			String path = file.getPath();
			path = path.substring(0,path.lastIndexOf("\\")) + "\\classes";
		return path;
	}

//		public static void main(String[] args) {
		public void main(){
	        try {
	            final ServerSocket server = new ServerSocket(10010);
	            Thread th = new Thread(new Runnable() {

	                @Override
	                public void run() {
	                    while (true) {
	                        try {
	                           System.out.println("开始监听（文件）。。。");
	                           Socket socket = server.accept();
	                           System.out.println("有链接（文件）");
	                           RecFileUtils test = new RecFileUtils();
	                           test.receiveFile(socket);
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

	    private void receiveFile(Socket socket) throws IOException {
	        byte[] inputByte = null;
	        int length = 0;
	        DataInputStream dis = null;
	        FileOutputStream fos = null;
	        File file_encrypt = null;
	        String encrypt_title = "";
	        String name = "";
	        int byte_len = 0;
	        try {
	        	dis = new DataInputStream(socket.getInputStream());
	        	name = dis.readUTF();
	        	encrypt_title = pre_Recpath + "\\head_img_encrypt\\"+createRandomNo()+"encrypt"+name;
	        	file_encrypt = new File(encrypt_title);
	        	byte_len = dis.readInt();
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
	            	String decrypt_title = pre_Recpath + "\\head_img_decrypt\\" + createRandomNo() +"decrypt"+name;
	            	decryptFile(encrypt_title,decrypt_title,byte_len);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	    
	    public static void decryptFile(String en_path,String de_path, int byte_len) {
	    	InputStream is_1 = null;
	    	OutputStream out_1 = null;
			try {
				is_1 = new FileInputStream(en_path);
	    		out_1 = new FileOutputStream(de_path);
	    		DesUtil desUtil = new DesUtil("你好", byte_len);
	
	        	
	    		byte[] buffer_1 = new byte[is_1.available()];
	    		is_1.read(buffer_1);
	    		
	    		byte[] buffer_decrypt = desUtil.deal(buffer_1, 0);
	    		out_1.write(buffer_decrypt, 0,buffer_decrypt.length);
	    		is_1.close();
	    		out_1.close();
	    		System.out.println("***********解密成功*********");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    private static String createRandomNo(){
	    	String randomNo = null;
	    	//改变输出格式（自己想要的格式） 
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS"); 
	    	//得到字符串时间 
	    	randomNo = formatter.format(new Date()).substring(11)+num;
	    	num++;
	    	return randomNo;
	    }

}
