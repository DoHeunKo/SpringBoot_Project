package com.hk.study.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.study.command.RegistCommand;
import com.hk.study.command.RoomCreateCommand;
import com.hk.study.dtos.FileDto;
import com.hk.study.dtos.RoomDto;
import com.hk.study.dtos.UserDto;
import com.hk.study.service.FileService;
import com.hk.study.service.RoomService;
import com.hk.study.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import javassist.expr.NewArray;

@Controller
@RequestMapping(value="/room")
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/roomCreateForm")
	public String roomCreateForm(Model model, HttpServletRequest request) {
//		System.out.println("방생성 폼이동");
//		session.getAttribute("id");
		model.addAttribute("roomCreateCommand",new RoomCreateCommand());
		
		return "thymeleaf/room/roomCreateForm";
	}
	
	@PostMapping(value="/roomCreate")
	public String regist(@Validated RoomCreateCommand roomCreateCommand,
						BindingResult result,
						@RequestParam("user_no") String user_no,
						Model model,HttpServletRequest request) {
		
		int euser_no=Integer.parseInt(user_no);
		//유효값 처리
		if(result.hasErrors()) {
			return "thymeleaf/room/roomCreateForm";
		}
		
		try {
			roomService.roomRegist(roomCreateCommand,request,euser_no);
			
			return "redirect:/user/userMainForm";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(roomCreateCommand.getTitle());
			System.out.println(roomCreateCommand.getContent());
			System.out.println(roomCreateCommand.getCategory());
			System.out.println(roomCreateCommand.getUpload());
			System.out.println(roomCreateCommand.getChat());
			System.out.println(roomCreateCommand.getMax_num());

			return "thymeleaf/room/roomCreateForm";
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
	public String roomDetail(Integer room_no,Model model) {
//		System.out.println(room_no);
		RoomDto dto=roomService.roomDetail(room_no);
		
//		if(dto==null) {
//			System.out.println("null");
//		}else {
//			System.out.println(dto.getRoom_content());
//			System.out.println(dto.getRoom_host());
//			System.out.println(dto.getRoom_count());
//			System.out.println(dto.getRoom_max());
//			System.out.println(dto.getRoom_image());
//		}
		model.addAttribute("dto",dto);

		return "thymeleaf/room/roomDetail";
	}
	
	
	@GetMapping("/hostChk")
	@ResponseBody
	public Map<String,String> hostChk(String host){
		String resultHost=roomService.hostChk(host);
//		System.out.println(resultHost);
		Map<String,String> map=new HashMap<>();
		map.put("host", resultHost);
		return map;
	}
	
	@GetMapping("/joinChk")
	@ResponseBody
	public Map<String,String> joinChk(String no,String rno){
		System.out.println(no);
		System.out.println(rno);
		String resultno=roomService.joinChk(no,rno);
		Map<String,String> map=new HashMap<>();
		map.put("no", resultno);
		return map;
	}
	
	
	@PostMapping(value="/roomJoin")
	public String roomJoin(Model model,@RequestParam("room_no") String room_no
			,@RequestParam("user_no") String user_no) {
		int eroom_no=Integer.parseInt(room_no);
		int euser_no=Integer.parseInt(user_no);
		System.out.println(eroom_no + " : " + euser_no);
		roomService.roomJoin(eroom_no,euser_no);
		return "redirect:/user/userMainForm";
	}
	
	@GetMapping("/userMaxJoin")
	@ResponseBody
	public Map<String,String> userMaxJoin(String no,String rno) {
		int eno=Integer.parseInt(no);
		int erno=Integer.parseInt(rno);
		
		int num=roomService.userMaxJoin(eno,erno);
//		System.out.println("컨트롤러 num"+num);
		Map<String,String> map=new HashMap<>();
		map.put("no", num+"");
		return map;
	}
	
	@PostMapping(value="/upload")
	public String upload(MultipartRequest multipartRequest
			,@RequestParam("room_no") String room_no,Model model) {
		int eroom_no=Integer.parseInt(room_no);
		System.out.println("화면으로부터 받은값: (upload)"+eroom_no);
		try {
			roomService.upload(multipartRequest,eroom_no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "thymeleaf/room/roomDetail";
		}
		//리다이렉트할 때 model에 roomDto에해당하는 dto를 다시 넣어줘서 실행
//		RoomDto dto=roomService.roomDetail(eroom_no);
//		model.addAttribute("dto",dto);
		return "redirect:/room/roomDetail?room_no="+eroom_no;
	}
	
	@GetMapping("/download")
	public void download(String file_no,HttpServletRequest request,
							HttpServletResponse response) {
		System.out.println("다운로드: "+file_no);
		String filePath="C:/Users/user/git/SpringBoot_Project/study_project/src/main/resources/upload";
		int efile_no=Integer.parseInt(file_no);
		FileDto fdto=roomService.getFileInfo(efile_no);
		
		try {
			fileService.fileDownload(filePath, 
					fdto.getFile_name(), fdto.getFile_fname(), 
					request, response);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
	}
}

