package com.hk.study.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.study.dtos.UserDto;

@Mapper
public interface UserMapper {

	//회원가입
	public boolean userRegist(UserDto dto);
	//로그인
	public UserDto userLogin(String id);
	//아이디 중복체크
	public String idChk(String id);
}
