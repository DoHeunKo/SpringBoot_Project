package com.hk.study.dtos;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias(value="RoomDto")
public class RoomDto {
	private int room_no;
	private String room_title;
	private String room_content;
	private String room_category;
	private String room_host;
	private String room_upload;
	private String room_chat;
	private Integer room_count;
	private Integer room_max;
	private String room_image;
	
	private List<UserDto> userDto;
	private List<FileDto> fileDto;
}
