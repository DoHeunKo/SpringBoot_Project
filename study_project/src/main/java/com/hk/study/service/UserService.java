package com.hk.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.study.command.LoginCommand;
import com.hk.study.command.RegistCommand;
import com.hk.study.dtos.UserDto;
import com.hk.study.mapper.UserMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public boolean userRegist(RegistCommand registCommand) {
		UserDto dto=new UserDto();
		dto.setUser_id(registCommand.getId());
		dto.setUser_name(registCommand.getName());
		dto.setUser_password(registCommand.getPassword());
		dto.setUser_email(registCommand.getEmail());
		dto.setUser_address(registCommand.getAddress());
		return userMapper.userRegist(dto);
	}
	
	public String userLogin(LoginCommand loginCommand, HttpServletRequest request) {
		UserDto dto=userMapper.userLogin(loginCommand.getId());
		if(dto != null) {
			if(loginCommand.getPassword().equals(dto.getUser_password())) {
				request.getSession().setAttribute("dto", dto);
				if(dto.getUser_role().equals("MANAGER")){
					return "thymeleaf/manager/managerMainForm";
				}else {
					return "thymeleaf/user/userMainForm";
				}
				
			}
			else {
				System.out.println("비밀번호 틀림");
				return "thymeleaf/user/loginForm";
			}
		}else {
			System.out.println("회원 정보 없음, 회원가입 필요");
			return "thymeleaf/user/loginForm";
		}
	}
	
	public String idChk(String id) {
		return userMapper.idChk(id);
	}
	
}
