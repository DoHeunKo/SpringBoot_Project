package com.hk.study.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hk.study.command.LoginCommand;
import com.hk.study.command.RegistCommand;
import com.hk.study.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired UserService userService;
	
	@GetMapping("/loginForm")
	public String login(Model model) {
		model.addAttribute("loginCommand",new LoginCommand());
		return "thymeleaf/user/loginForm";
	}
	
	@PostMapping("/login")
	public String login(@Validated LoginCommand loginCommand,
						BindingResult result,
						HttpServletRequest request, Model model,HttpSession session) {
		
		if(result.hasErrors()) {
			return "thymeleaf/user/loginForm";
		}
		session.setAttribute("id", loginCommand.getId());
		return userService.userLogin(loginCommand, request);
	}
	
	@GetMapping(value="/registForm")
	public String addUserForm(Model model) {
		System.out.println("회원가입 폼이동");
		model.addAttribute("registCommand",new RegistCommand());
		return "thymeleaf/user/registForm";
	}
	
	@PostMapping(value="/regist")
	public String regist(@Validated RegistCommand registCommand,
						BindingResult result,
						Model model) {
		//유효값 처리
		if(result.hasErrors()) {
			return "thymeleaf/user/registForm";
		}
		try {
			boolean isS=userService.userRegist(registCommand);
			return "redirect:/user/loginForm";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/user/registForm";
		}
	}
	@GetMapping(value="/userMainForm")
	public String userMainForm(Model model) {
		
		return "thymeleaf/user/userMainForm";
	}
	
	@GetMapping("/idChk")
	@ResponseBody
	public Map<String,String> idChk(String id){
		System.out.println("아이디 중복 체크");
		String resultId=userService.idChk(id);
		Map<String,String> map=new HashMap<>();
		map.put("id", resultId);
		return map;
	}
	@GetMapping("/emailChk")
	@ResponseBody
	public Map<String,String> emailChk(String email){
		System.out.println("이메일 중복 체크");
		String resultEmail=userService.emailChk(email);
		Map<String,String> map=new HashMap<>();
		map.put("email", resultEmail);
		return map;
	}
}
