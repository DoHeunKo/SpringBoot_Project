package com.hk.study.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.study.dtos.UserDto;
import com.hk.study.dtos.UserRoomDto;

@Mapper
public interface UserMapper {

	//회원가입
	public boolean userRegist(UserDto dto);
	//userRoom에 동시에 추가
	public boolean userRoomRegist(UserRoomDto dto);
	//로그인
	public UserDto userLogin(String id);
	//아이디 중복체크
	public String idChk(String id);
	//이메일 중복체크
	public String emailChk(String email);
}
