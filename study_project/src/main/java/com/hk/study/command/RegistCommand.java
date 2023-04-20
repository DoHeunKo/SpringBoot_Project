package com.hk.study.command;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistCommand {
	
	@NotBlank(message="아이디 입력")
	private String id;
	@NotBlank(message="비밀번호 입력 : 1~15자리")
	@Length(min = 1, max=15)
	private String password;
	@NotBlank(message="이름 입력")
	private String name;
	@NotBlank(message="이메일 입력")
	private String email;
	@NotBlank(message="주소 입력")
	private String address;
	
}
