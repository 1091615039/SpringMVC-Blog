package com.tianmaying.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tianmaying.form.BlogCreateForm;
import com.tianmaying.model.Blog;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
//import com.tianmaying.service.UserService;

@Controller
public class CreateBlogController {

	@Autowired
	private BlogService blogService;
	//@Autowired
	//private UserService userService;
	
    @GetMapping("/blogs/create")
    public String get(Model model, HttpSession session) {
//    	if(session.getAttribute("CURRENT_USER") == null) {
//			return "redirect:/login";
//		}
    	model.addAttribute("blog", new Blog());
    	model.addAttribute("operate", "create");
        return "create";
    }

    @PostMapping("/blogs")
    public String post(@ModelAttribute("blog") @Valid BlogCreateForm form, BindingResult result , HttpSession session) {   
    	//User author = userService.findByName("tianmaying");
//    	if(session.getAttribute("CURRENT_USER") == null) {
//			return "redirect:/login";
//		}
    	User author = null;
    	if(session.getAttribute("CURRENT_USER") != null) {
    		author = (User) session.getAttribute("CURRENT_USER");
    	}
    	Blog blog = form.toBlog(author);
    	if(result.hasErrors()) {
    		return "create";
    	}
    	blogService.createBlog(blog); 
    	return "redirect:/blogs/" + blog.getId();
    }
}
