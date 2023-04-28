package com.hk.study.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.study.command.RoomCreateCommand;
import com.hk.study.dtos.FileDto;
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
	@Autowired
	private FileService fileService;
	
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
	
	public RoomDto roomDetail(Integer room_no) {
		System.out.println(room_no);
		RoomDto dto=roomMapper.roomDetail(room_no);
//		System.out.println(dto.getRoom_host());
		return dto;
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
	
	public int userMaxJoin(int user_no,int room_no) {
		//한사람이 참여하고있는 스터디 개수 
		int num=roomMapper.userMaxJoin(user_no);
		//room에서 현재 count를 가져와서 max와비교
		RoomDto dto=roomMapper.roomDetail(room_no);
		int room_count=dto.getRoom_count();
		int room_max=dto.getRoom_max();
		if(room_count==room_max) {
			num=100;
		}
		System.out.println("room_max : ");
		System.out.println("service:"+num);
		return num;
	}
	
	public void upload(MultipartRequest multipartRequest,Integer room_no) throws IllegalStateException, IOException {
		System.out.println("roomService:room_no : "+room_no);
		if(!multipartRequest.getFiles("filename").isEmpty()) {
			String filePath="C:/Users/user/git/SpringBoot_Project/study_project/src/main/resources/upload";
			List<FileDto> uploadFileList=fileService.uploadFiles(filePath, multipartRequest,room_no);
			for(FileDto fdto: uploadFileList) {
				roomMapper.insertFile(fdto);
			}
		}
	}
	public FileDto getFileInfo(int file_no) {
		return roomMapper.getFileInfo(file_no);
	}

}
