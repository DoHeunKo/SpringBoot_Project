package com.hk.study.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginCommand {
	@NotBlank(message="아이디 입력")
	private String id;
	@NotBlank(message="비밀번호 입력")
	private String password;
}
