package com.tianmaying.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tianmaying.form.UserRegisterForm;
import com.tianmaying.model.User;
import com.tianmaying.service.UserService;

@Controller
public class UserInfoSetController {

	@Value("${file.dir}")
	private String ROOT;
	
	private final ResourceLoader resourceLoader;
	@Autowired
	private UserService userService;
	
	@Autowired
	public UserInfoSetController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@GetMapping("/profile")
	public String get(Model model, HttpSession session) {
		User user = (User) session.getAttribute("CURRENT_USER");
//		if(user == null) {
//			return "redirect:/login";
//		}
		model.addAttribute("user", user);
		return "profile";
	}
	
	@GetMapping("/profile/{filename:.+}")
	public ResponseEntity<?> getFile(Model model, HttpSession session, @PathVariable String filename) {
		User user = (User) session.getAttribute("CURRENT_USER");
		if(user.getAvatar() == null) {
			return ResponseEntity.ok(resourceLoader.getResource("classpath:" + Paths.get("/static/img/", "catty.jpeg").toString()));
		}
		try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
	}
	
	@PostMapping("/profile")
	public String post(Model model, HttpSession session, @RequestParam(value="file", required=false) MultipartFile file,
			RedirectAttributes redirectAttributes, @ModelAttribute("user") @Valid UserRegisterForm form,  BindingResult result) throws IOException {
		User currentUser = (User) session.getAttribute("CURRENT_USER");
		User oldUser = currentUser;
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "更新信息");
			session.setAttribute("CURRENT_USER", oldUser);
			return "redirect:/profile";
		}
		currentUser = form.toUser();
		if(currentUser != null) {
			currentUser.setId(oldUser.getId());
			if(currentUser.getEmail() == null) {
				currentUser.setEmail(oldUser.getEmail());
			}
			if(currentUser.getPassword() == null) {
				currentUser.setPassword(oldUser.getPassword());
			}
			if(currentUser.getAvatar() == null) {
				model.addAttribute("classpath", "path");
			} else {
				model.addAttribute("classpath", "root");
			}
			if(file != null && !file.isEmpty()) {
				String filename = currentUser.getId() + ".jpg";
				Files.copy(file.getInputStream(), Paths.get(ROOT, filename));
				currentUser.setAvatar(Paths.get(ROOT, filename).toString());
			}
			currentUser = userService.update(currentUser);
			if(currentUser != null) {
				redirectAttributes.addFlashAttribute("message", "更新成功");
				session.setAttribute("CURRENT_USER", currentUser);
				return "redirect:/profile";
			}
		}
		redirectAttributes.addFlashAttribute("message", "更新失败");
		session.setAttribute("CURRENT_USER", oldUser);
		return "redirect:/profile";
	}
}
