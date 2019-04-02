package com.test.graduation;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTest {
	
	private static String fileName = "D:\\testDemo\\123.jpg";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int length = 0;
        byte[] sendByte = null;
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
          try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 33456),10 * 1000);
            dos = new DataOutputStream(socket.getOutputStream());
            File file = new File(fileName);
           //des加密 
            FileDesTest.saveDesKey();
    		FileDesTest.encrypt(fileName,"D:\\testDemo\\123encrypt.jpg");
    		FileDesTest.decrypt("D:\\testDemo\\123encrypt.jpg","D:\\testDemo\\123decrypt.jpg");
    		
    		File file_encrypt = new File("D:\\testDemo\\123encrypt.jpg");
    		//加密文件若存在 则传输
    		if(file_encrypt.exists()){
    			System.out.println("加密成功");
    			fis = new FileInputStream(file_encrypt);
                sendByte = new byte[1024];
                dos.writeUTF(file_encrypt.getName());
                while((length = fis.read(sendByte, 0, sendByte.length))>0){
                	         	
                	dos.write(sendByte,0,length);
                	dos.flush();
                }
                System.out.println("加密文件已传输");
    		}    
            } catch (Exception e) {

            } finally{
                if (dos != null)
                	dos.close();
                if (fis != null)
                	fis.close();
                if (socket != null)
                    socket.close();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
