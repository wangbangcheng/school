package com.test.graduation;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTest {

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
            File file = new File("D:\\testDemo\\123.jpg");
            fis = new FileInputStream(file);
            sendByte = new byte[1024];
            dos.writeUTF(file.getName());
            while((length = fis.read(sendByte, 0, sendByte.length))>0){
            	         	
            	dos.write(sendByte,0,length);
            	dos.flush();
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
