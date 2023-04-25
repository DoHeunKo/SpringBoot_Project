package com.hk.study.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hk.study.command.RegistCommand;
import com.hk.study.command.RoomCreateCommand;
import com.hk.study.dtos.RoomDto;
import com.hk.study.dtos.UserDto;
import com.hk.study.service.RoomService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping(value="/room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@GetMapping("/roomCreateForm")
	public String roomCreateForm(Model model, HttpServletRequest request) {
		System.out.println("방생성 폼이동");
//		session.getAttribute("id");
		model.addAttribute("roomCreateCommand",new RoomCreateCommand());
		
		return "thymeleaf/room/roomCreateForm";
	}
	
	@PostMapping(value="/roomCreate")
	public String regist(@Validated RoomCreateCommand roomCreateCommand,
						BindingResult result,
						Model model,HttpServletRequest request) {
		//유효값 처리
		if(result.hasErrors()) {
			return "thymeleaf/room/roomCreateForm";
		}
		
		try {
			boolean isS=roomService.roomRegist(roomCreateCommand,request);
			return "redirect:/user/userMainForm";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(roomCreateCommand.getTitle());
			System.out.println(roomCreateCommand.getContent());
			System.out.println(roomCreateCommand.getCategory());
			System.out.println(roomCreateCommand.getUpload());
			System.out.println(roomCreateCommand.getChat());
			System.out.println(roomCreateCommand.getMax_num());

			return "redirect:/room/roomCreateForm";
		}
		
	}
	@GetMapping(value="/roomList")
	public String roomList(Model model) {

		List<RoomDto> list=roomService.roomList();
		model.addAttribute("list",list);
//		model.addAttribute("delBoardCommand",new DelBoardCommand());
		return "thymeleaf/room/roomList";
	}
	
	@GetMapping(value="/roomDetail")
	public String roomDetail(int room_no,Model model) {
		
		RoomDto dto=roomService.roomDetail(room_no);
		
		if(dto==null) {
			System.out.println("null");
		}else {
			System.out.println(dto.getRoom_content());
			System.out.println(dto.getRoom_host());
			System.out.println(dto.getRoom_count());
			System.out.println(dto.getRoom_max());
			System.out.println(dto.getRoom_image());
		}
		model.addAttribute("dto",dto);

		return "thymeleaf/room/roomDetail";
	}
	
}

