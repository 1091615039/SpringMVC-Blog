package com.tianmaying.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "403 Forbidden")
public class BlogNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public BlogNotFoundException(String message) {
	        super(message);
	    }
/*
	public BlogNotFoundException(String msg) {
		this.msg = msg;
	}

	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}*/

}
