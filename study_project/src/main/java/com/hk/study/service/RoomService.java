package com.hk.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.study.command.RoomCreateCommand;
import com.hk.study.dtos.RoomDto;
import com.hk.study.dtos.UserDto;
import com.hk.study.mapper.RoomMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class RoomService {
	@Autowired
	private RoomMapper roomMapper;
	
	public boolean roomRegist(RoomCreateCommand roomCreateCommand,HttpServletRequest request) {
		RoomDto dto=new RoomDto();
		UserDto udto=(UserDto)request.getSession().getAttribute("dto");
		dto.setRoom_title(roomCreateCommand.getTitle());
		dto.setRoom_content(roomCreateCommand.getContent());
		dto.setRoom_category(roomCreateCommand.getCategory());
		dto.setRoom_host(udto.getUser_id());
		dto.setRoom_upload(roomCreateCommand.getUpload());
		dto.setRoom_chat(roomCreateCommand.getChat());;
		dto.setRoom_count(Integer.parseInt(roomCreateCommand.getCount()));
		dto.setUser_no(udto.getUser_no());
		dto.setRoom_image(roomCreateCommand.getFilename());
	
		return roomMapper.roomRegist(dto);
	}
	
	public List<RoomDto> roomList(){
		return roomMapper.roomList();
		
	}
}
