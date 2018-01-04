package com.tianmaying.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
//import com.tianmaying.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private BlogService blogService;
	//@Autowired
	//private UserService userService;
	
	@GetMapping("/admin")
	public String get(@RequestParam("next") Optional<String> next, @PageableDefault(value = 2, sort = "id", direction = Direction.DESC) Pageable pageable, Model model, HttpSession session) {
		//User user = userService.findByName("tianmaying");
//		if(session.getAttribute("CURRENT_USER") == null) {
//			return "redirect:/login";
//		}
		User user = (User) session.getAttribute("CURRENT_USER");
		model.addAttribute("blogs", blogService.findBlogs(user, pageable));
		return "admin";
	}
}
