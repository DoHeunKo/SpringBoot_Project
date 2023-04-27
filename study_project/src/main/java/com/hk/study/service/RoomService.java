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
	
	
	@Transactional
	public void roomRegist(RoomCreateCommand roomCreateCommand,HttpServletRequest request,
			int user_no) {
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
		boolean isS1=roomMapper.roomRegist(dto);
		if(isS1) {
			System.out.println("방생성 완료");
		}
		System.out.println("방등록시 넘어오는 user_no"+user_no);
		System.out.println("넘어오는 room_no"+dto.getRoom_no());
		UserRoomDto urdto=new UserRoomDto();
		urdto.setUser_no(user_no);
		urdto.setRoom_no(dto.getRoom_no());
		boolean isS2=roomMapper.userRoom(urdto);
		if(isS2) {
			System.out.println("user_room테이블 갱신완료");
		}
	}
	
	public List<RoomDto> roomList(){
		return roomMapper.roomList();
	}
	
	public RoomDto roomDetail(int room_no) {
		System.out.println(room_no);
		RoomDto dto=roomMapper.roomDetail(room_no);
		System.out.println(dto.getRoom_host());
		return roomMapper.roomDetail(room_no);
	}
	
	public String hostChk(String host) {
		System.out.println(host);
		System.out.println(roomMapper.hostChk(host));
		return roomMapper.hostChk(host);
	}
	public String joinChk(String no,String rno) {
		UserRoomDto urdto=new UserRoomDto();
		
		urdto.setUser_no(Integer.parseInt(no));
		urdto.setRoom_no(Integer.parseInt(rno));
		return roomMapper.joinChk(urdto);
	}
	
	@Transactional
	public void roomJoin(int room_no,int user_no) {
		
		UserRoomDto urdto=new UserRoomDto();
		urdto.setUser_no(user_no);
		urdto.setRoom_no(room_no);
	
		boolean isS1=roomMapper.plusJoin(room_no);
		if(isS1) {
			System.out.println("count+1");
		}
		boolean isS2=roomMapper.userRoom(urdto);
		if(isS2) {
			System.out.println("userRoom테이블 추가");
		}
	}
	
	public int userMaxJoin(int user_no,int room_max) {
		int num=roomMapper.userMaxJoin(user_no);
		if(room_max==num) {
			num=100;
		}
		System.out.println("room_max : "+ room_max);
		System.out.println("service:"+num);
		return num;
	}

}
