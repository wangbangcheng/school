package com.wbc.graduation.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wbc.graduation.domain.Admin;
import com.wbc.graduation.domain.User;
import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.service.AdminService;
import com.wbc.graduation.service.UserService;
import com.wbc.graduation.util.MD5diyUtils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MD5diyUtils md5diyUtils;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String LoginAdmin(@Param("name")String name,@Param("password")String password,Model model) {
		String password_md5 = "";
		Admin admin = null;
		try {
			password_md5 = md5diyUtils.encrypt16(password);
		} catch (Md5EncodeException e) {
			e.printStackTrace();
		}
		admin = adminService.checkAdmin(name);
		
		if(name==null||"".equals(name)){  //没有输入姓名
            model.addAttribute("msg1","请输入用户名或手机号");
        }else if(admin==null||"".trim().equals(admin)){ //输入姓名但是姓名错误
            model.addAttribute("msg1","用户不存在，请先注册");
            return "NewLogin";
        }else if(admin!=null &(password==null||"".equals(password))){ 
            model.addAttribute("msg2","请输入密码");
        }else if(admin!=null &!(admin.getPassword().equals(password_md5))) { 
            model.addAttribute("msg2","密码错误");
        }else if (admin!=null &&admin.getPassword().equals(password_md5)){ //姓名密码均正确
        	System.out.println(admin.getName()+":"+admin.getPassword());
        	model.addAttribute("admin",admin);
            return "main";
        }
		return "NewLogin";
		
	}
	
	@RequestMapping("/register")
	public String RegisterAdmin(@Param("name")String name,@Param("phone")String phone,@Param("password")String password,Model model) {
		String password_md5 = "";
		Admin admin = null;
		Admin admin_register = null;
		try {
			password_md5 = md5diyUtils.encrypt16(password);
		} catch (Md5EncodeException e) {
			e.printStackTrace();
		}
		if(phone == null || password == null){
			model.addAttribute("alert","手机或密码不能为空");
		}
		admin = adminService.checkAdmin(name);
		if( admin != null){  
            model.addAttribute("alert","该用户名已注册");
        }else{
        	admin = adminService.checkAdmin(phone);
        	if( admin != null){  
                model.addAttribute("alert","该手机号已注册");
            }
        	admin_register = new Admin();
        	admin_register.setPhone(phone);
        	admin_register.setPassword(password_md5);
        	if(!"".equals(name)) {
        		admin_register.setName(name);
        	}
        	boolean flat = adminService.registerAdmin(admin_register);
        	if(flat){
        		model.addAttribute("alert","注册成功！！！！！");
        		return "redirect:../NewLogin.jsp";
        	}else{
        		model.addAttribute("alert","注册失败，环境异常！！");
        	}
        	
        }
		return "NewRegister";
		
		
	}

}
