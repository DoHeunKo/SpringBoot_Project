package com.hk.study.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomCreateCommand {
	
	
	@NotBlank(message="스터디 이름 입력")
	private String title;
	@NotBlank(message="스터디 소개글 입력")
	private String content;
	@NotBlank(message="카테고리 선택")
	private String category;
	private String upload;
	private String chat;
	@NotBlank(message = "참여인원 선택")
	private String max_num;
	private String filename;
	

}
