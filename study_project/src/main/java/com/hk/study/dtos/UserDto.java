package com.hk.study.dtos;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Alias(value="UserDto")
public class UserDto {
	private int user_no;
	private String user_id;
	private String user_name;
	private String user_password;
	private String user_email;
	private String user_address;
	private String user_role;
	private String user_delflag;
	private int room_no;
}
