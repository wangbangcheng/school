package com.wbc.graduation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wbc.graduation.exception.Md5EncodeException;
import com.wbc.graduation.util.MD5diyUtils;


@Controller
@RequestMapping("/page")
public class PageController {
	@Autowired
	private MD5diyUtils MD5diyUtils;
	private static int i = 0;
	@RequestMapping("/login")
    public String Login() {
		return "Login";
    }
	
	@RequestMapping("/register")
    public String register() {
        return "register";
    }
}
