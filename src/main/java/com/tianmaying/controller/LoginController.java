package com.tianmaying.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tianmaying.model.User;
import com.tianmaying.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;

    @GetMapping
    public String get(Model model, @RequestParam("next") Optional<String> next) {  	
    	model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping
    public String post(User user, @RequestParam("next") Optional<String> next, HttpSession session) {	
    	User userLogin =userService.login(user.getEmail(), user.getPassword());
    	session.setAttribute("CURRENT_USER", userLogin);
        return "redirect:".concat(next.orElse("/" + userLogin.getName()));
    }

}
