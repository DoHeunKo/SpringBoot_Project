package com.hk.study.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hk.study.dtos.RoomDto;
import com.hk.study.dtos.UserRoomDto;

@Mapper
public interface RoomMapper {
	//방생성
	public boolean roomRegist(RoomDto dto);
	//방조회
	public List<RoomDto> roomList();
	//방상세조회
	public RoomDto roomDetail(int room_no);
	//방장참여불가
	public String hostChk(String host);
	//참여한방확인
	public String joinChk(String user_no);
	//참여자증가
	public boolean plusJoin(int room_no);

	//user_room관계테이블
	public boolean userRoom(UserRoomDto dto);
	
}
