package com.tianmaying.controller;

import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
	
	@Autowired
    private BlogService blogService;
	@Autowired
    private UserService userService;

    @Autowired
    public IndexController(BlogService blogService,UserService userService) {
        this.blogService = blogService;
        this.userService = userService;
    }
/*
    @GetMapping("/{username}")
    //使用@RequestParam获取参数
    public String get(@PathVariable("username") String username, Model model,
    		 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        // Your Code goes here
        // 渲染模板list.html
    	User user = userService.findByName(username);
    	int start = (page - 1) * size;
    	List<Blog> blogs = blogService.findBlogs(user).subList(start, start + size);
    	model.addAttribute("blogs", blogs);
        return "list";
    }
*/
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
    @GetMapping("/{username}")
    public String getEntryByPageable(@PathVariable("username") String username, @PageableDefault(value=2,sort= {"id"},direction=Sort.Direction.DESC)Pageable pageable, Model model) {
    	User user = userService.findByName(username);
    	model.addAttribute("blogs", blogService.findBlogs(user, pageable));
    	return "list";
    }

}
