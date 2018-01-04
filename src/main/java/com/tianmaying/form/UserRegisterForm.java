package com.tianmaying.form;

import javax.validation.constraints.Size;

import com.tianmaying.model.User;

public class UserRegisterForm {

	@Size(min = 6, max = 30)
	private String name;
	
	@Size(min = 6, max = 30)
	private String password;
	
	@org.hibernate.validator.constraints.Email
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public User toUser() {
		User user = new User();
		user.setName(this.name);
		user.setPassword(this.password);
		user.setEmail(this.email);
		return user;
		
	}
}
