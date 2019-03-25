package com.wbc.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/page")
public class PageController {
	
	@RequestMapping("/login")
    public String Login() {
        return "Login";
    }
	
	@RequestMapping("/register")
    public String register() {
        return "register";
    }
}
