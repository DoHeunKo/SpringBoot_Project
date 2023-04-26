package com.hk.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hk.study.command.RoomCreateCommand;
import com.hk.study.dtos.RoomDto;
import com.hk.study.dtos.UserDto;
import com.hk.study.dtos.UserRoomDto;
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
		dto.setRoom_max(Integer.parseInt(roomCreateCommand.getMax_num()));
		dto.setRoom_image(roomCreateCommand.getFilename());
	
		return roomMapper.roomRegist(dto);
	}
	
	public List<RoomDto> roomList(){
		return roomMapper.roomList();
	}
	
	public RoomDto roomDetail(int room_no) {
		return roomMapper.roomDetail(room_no);
	}
	
	public String hostChk(String host) {
		System.out.println(host);
		System.out.println(roomMapper.hostChk(host));
		return roomMapper.hostChk(host);
	}
	public String joinChk(String no) {
		return roomMapper.joinChk(no);
	}
	@Transactional
	public void roomJoin(int room_no,int user_no) {
		
		UserRoomDto dto=new UserRoomDto();
		dto.setUser_no(user_no);
		dto.setRoom_no(room_no);
		boolean isS1=roomMapper.plusJoin(room_no);
		if(isS1) {
			System.out.println("count+1");
		}
		boolean isS2=roomMapper.userRoom(dto);
		if(isS2) {
			System.out.println("userRoom테이블 추가");
		}
	}
}
