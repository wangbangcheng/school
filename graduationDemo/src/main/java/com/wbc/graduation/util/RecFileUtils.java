package com.wbc.graduation.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class RecFileUtils implements Runnable{

	private static int num = 1;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public static void serverRecSocket() {
        try {
            final ServerSocket server = new ServerSocket(5203);
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
        try {
        	dis = new DataInputStream(socket.getInputStream());
            
        	fos = new FileOutputStream(new File("E:\\serverTest\\"+createRandomNo()+dis.readUTF()));
            inputByte = new byte[1024];
            System.out.println("开始接收数据...");
            while (true) {
                if (dis != null) {
                    length = dis.read(inputByte, 0, inputByte.length);
                }
                if (length == -1) {
                    break;
                }
                System.out.println();
                System.out.println(length);
                fos.write(inputByte, 0, length);
                fos.flush();
            }
            System.out.println("完成接收");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null)
            	fos.close();
            if (dis != null)
            	dis.close();
            if (socket != null)
                socket.close();
        }
    }
    private static String createRandomNo(){
    	String randomNo = null;
    	//改变输出格式（自己想要的格式） 
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmSS"); 
    	//得到字符串时间 
    	randomNo = formatter.format(new Date())+num;
    	num++;
    	return randomNo;
    }
}
