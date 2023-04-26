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
@AllArgsConstructor
@NoArgsConstructor
@Alias(value="UserRoomDto")
public class UserRoomDto {
	private int user_no;
	private int room_no;
}
