package com.wbc.graduation.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;







import com.wbc.graduation.domain.User;
import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.service.UserService;
import com.wbc.graduation.util.MD5diyUtils;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private MD5diyUtils md5diyUtils;
	
	@RequestMapping("/login")
	public String Login(@Param("username")String username,@Param("password")String password,Model model){
		String password_md5 = "";
		try {
			password_md5 = md5diyUtils.encrypt16(password);
		} catch (Md5EncodeException e) {
			e.printStackTrace();
		}
		User user = userService.userLoginCheck(username);
		if(username==null||"".equals(username)){  //没有输入姓名
            model.addAttribute("msg1","请输入姓名");
        }else if(user==null||"".trim().equals(user)){ //输入姓名但是姓名错误
            model.addAttribute("msg1","账户不存在，请先注册");
            return "NewLogin";
        }else if(user!=null &(password==null||"".equals(password))){ 
            model.addAttribute("msg2","请输入密码");
        }else if(user!=null &!(user.getPassword().equals(password_md5))) { 
            model.addAttribute("msg2","密码错误");
        }else if (user!=null &&user.getPassword().equals(password_md5)){ //姓名密码均正确
        	System.out.println(user.getUserName()+":"+user.getPassword());
        	model.addAttribute("username",user.getUserName());
            return "main";
        }
		return "NewLogin";

	}
	
	@RequestMapping("/register")
	public String register(@Param("username")String username,@Param("password")String password,Model model){
		String password_md5 = "";
		try {
			password_md5 = md5diyUtils.encrypt16(password);
		} catch (Md5EncodeException e) {
			e.printStackTrace();
		}
		if(username == null || password == null){
			model.addAttribute("alert","用户名或密码不能为空");
		}
		User user = userService.userLoginCheck(username);
		if( user != null){  
            model.addAttribute("alert","该用户名已被注册");
        }else{
        	User user_register = new User(username,password_md5);
        	boolean flat = userService.registerUser(user_register);
        	if(flat){
        		model.addAttribute("alert","注册成功！！！！！");
        	}else{
        		model.addAttribute("alert","注册失败，环境异常！！");
        	}
        	
        }
		return "NewRegister";
	}
}
