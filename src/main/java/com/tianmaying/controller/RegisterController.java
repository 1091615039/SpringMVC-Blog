package com.tianmaying.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tianmaying.form.UserRegisterForm;
import com.tianmaying.model.User;
//import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	//@Autowired
	//private BlogService blogService;

    @GetMapping
    public String get(@ModelAttribute("message") String message, Model model) {
    	model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String post(@ModelAttribute("user") @Valid UserRegisterForm form, BindingResult result, final RedirectAttributes redirectAttributes) {
    	User user = form.toUser();
    	userService.register(user);
    	if(result.hasErrors()) {
    		return "register";
    	}
    	redirectAttributes.addFlashAttribute("message", "success");
    	//blogService.createBlog(new Blog("title", "content", user));
        return "redirect:/admin";
    }

}
