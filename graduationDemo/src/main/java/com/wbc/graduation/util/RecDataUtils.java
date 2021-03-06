
package com.wbc.graduation.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


import com.wbc.graduation.domain.User;
import com.wbc.graduation.service.UserService;
import com.wbc.graduation.serviceImpl.UserServiceImpl;


@Component
//@DependsOn({""})
public class RecDataUtils  implements Runnable{
	
	@Autowired
    private  UserService userService;
	
	/*public RecDataUtils() {
		serverRecData();
	}*/
	
    public void serverRecData() {
		try {
			//1.建立一个服务器Socket(ServerSocket)绑定指定端口
			final ServerSocket server = new ServerSocket(10011);
	        Thread th = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							//2.使用accept()方法阻止等待监听，获得新连接
				            System.out.println("开始监听...");
				            Socket socket = server.accept();
							System.out.println("有链接");
				            ReceiveData(socket);
						} catch (IOException e) {
							e.printStackTrace();
						}
			            
					}
				}
				
	        });
	        th.run();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void ReceiveData(Socket socket){
		String request = "";
    	String response = "";
    	String array[] = null; 
        try {
            //3.获得输入流
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //获得输出流
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            //4.读取用户输入信息
            String rece=null;
            while(!((rece=br.readLine())==null)){ 
                request += rece;
            }
            System.out.println("我是服务器，用户信息为："+request);
            array = request.split("\\|");
            switch (array[0]) {
			case "1":	//登录
				response = loginCheck(array[1], array[2], array[3]);
				break;

			case "2":
				response = registerUser(array[1], array[2], array[3]);
				break;
			}
            System.out.println("向客户端发送反馈信息："+response);
            pw.write(response);
            pw.flush();
            //5.关闭资源
            pw.close();
            os.close();
            br.close();
            is.close();
            socket.close();
           
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	if(!"".equals(response)&&"1".equals(response.split("\\|")[0])) {
        		RecFileUtils recFileUtils = new RecFileUtils();
        		recFileUtils.main(array[3]);
        	}
        }
	}
	
    	
    public String loginCheck(String username,String password,String secretKey){
    	
    	User user = userService.userLoginCheck(username);
		if(username==null||"".equals(username)){  //没有输入姓名
			return "0|f|用户名不能为空";
        }else if(user==null||"".trim().equals(user)){ //输入姓名但是姓名错误
            return "0|f|不存在该用户";
        }else if(user!=null &(password==null||"".equals(password))){ 
        	return "0|f|密码不能为空";
        }else if(user!=null &!(user.getPassword().equals(password))) { 
        	return "0|f|密码错误";
        }else if (user!=null &&user.getPassword().equals(password)){ //姓名密码均正确
        	user.setHeadImg(secretKey);
        	userService.update(user);
            return "1|s|登录成功";
        }
		return "未知条件判定";
    }
    public  String registerUser(String username,String password,String secretKey){
    	
    	if(username == null || password == null){
    		return "0|f|用户名或密码不能为空";
		}
		User user = userService.userLoginCheck(username);
		if( user != null){  
			return "0|f|该用户名已被注册";
        }else{
        	User user_register = new User(username,password);
        	user_register.setHeadImg(secretKey);
        	boolean flat = userService.registerUser(user_register);
        	if(flat){
        		return "2|s|注册成功";
        		
        	}
        	
        }
		return "0|f|未知原因错误";
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

    
}
