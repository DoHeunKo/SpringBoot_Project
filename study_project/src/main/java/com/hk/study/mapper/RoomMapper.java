package com.hk.study.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hk.study.dtos.RoomDto;

@Mapper
public interface RoomMapper {
	//방생성
	public boolean roomRegist(RoomDto dto);
	//방조회
	public List<RoomDto> roomList();
	//방상세조회
	public RoomDto roomDetail(int room_no);
	
}
