package com.tianmaying.controller;

import com.tianmaying.model.Blog;
import com.tianmaying.service.BlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
	
	@Autowired
	private BlogService blogService;
	
    @GetMapping("/search")
    public String search(@RequestParam("key") String key, Model model) {
    	List<Blog> blogs = blogService.findBlogsByKey(key);   
    	model.addAttribute("blogs", blogs);
    	return "list";
    }
}
