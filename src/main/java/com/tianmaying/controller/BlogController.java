package com.tianmaying.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tianmaying.form.BlogCreateForm;
import com.tianmaying.model.Blog;
import com.tianmaying.model.Comment;
import com.tianmaying.model.User;
import com.tianmaying.service.BlogService;
import com.tianmaying.service.CommentService;

@Controller
public class BlogController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;


	@GetMapping("/blogs/{blogId}")
	public String get(Model model,@PathVariable("blogId") Long blogId) {	  
		Blog blog = blogService.findBlog(blogId);  
		List<Comment> comments =commentService.getCommentOfBlog(blog);	  
	    model.addAttribute("comments", comments);	  
	    model.addAttribute("blog", blog);	  
	    return "item";	  
	}
	  
	@PostMapping("/blogs/{blogId}/comments")
	@ResponseBody
	public Comment post(@RequestBody String content, HttpSession session, @PathVariable("blogId") Long blogId) {
		User user = (User) session.getAttribute("CURRENT_USER");
		Comment comment = new Comment();
		Blog blog = blogService.findBlog(blogId);
		comment.setBlog(blog);
		comment.setCommentor(user);
		comment.setContent(content.substring(8));
		comment.setCreatedTime(new Date());
		comment = commentService.createComment(comment);
		return comment;
	}
	
	@GetMapping("/blogs/{blogId}/edit")
	public String get(@PathVariable("blogId") long id, Model model) {
		Blog blog = blogService.findBlog(id);
		model.addAttribute("blog", blog);
		model.addAttribute("operate", "update");
		model.addAttribute("id", id);
		return "create";
	}
	
	@PutMapping("/blogs/{id}")
	public String put(@ModelAttribute("blog") @Valid BlogCreateForm form,  BindingResult result, HttpSession session,
			@PathVariable("id") Long id, Model model) throws Exception{		
		User user = (User) session.getAttribute("CURRENT_USER");
		if(!blogService.findBlog(id).getAuthor().getId().equals(user.getId())) {
			throw new BlogNotFoundException("403 Forbidden");
		}
		if(result.hasErrors()) {
			model.addAttribute("operate", "update");
			return "create";
		}
    	Blog blog = form.toBlog(user);
		blog.setId(id);
		blogService.updateBlog(blog);
		model.addAttribute("id", id);
		return "redirect:/blogs/" + blog.getId();
	}

	@DeleteMapping("/blogs/{id}")
	public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes, Model model) {
		Blog blog = blogService.findBlog(id);
		blogService.deleteBlog(blog);
		model.addAttribute("operate", "delete");
		redirectAttributes.addFlashAttribute("message", "delete success");
		return "redirect:/admin";
	}

}