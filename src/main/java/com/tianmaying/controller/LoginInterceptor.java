package com.tianmaying.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute("CURRENT_USER") != null) {
			return true;
		}
		response.sendRedirect("/login?next=".concat(request.getRequestURI()));
		return false;
	}
	
}
