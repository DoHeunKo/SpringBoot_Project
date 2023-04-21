package com.hk.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hk.study.command.RoomCreateCommand;

@Controller
@RequestMapping(value="/room")
public class RoomController {
	
	@GetMapping("/roomCreateForm")
	public String roomCreateForm(Model model) {
		System.out.println("방생성 폼이동");
		model.addAttribute("roomCreateCommand",new RoomCreateCommand());
		return "thymeleaf/room/roomCreateForm";
	}
}
