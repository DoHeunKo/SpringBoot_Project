package com.hk.study.dtos;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private int room_count;
	private int room_max;
	private String room_image;
}
