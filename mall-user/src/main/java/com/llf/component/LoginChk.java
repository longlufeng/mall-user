package com.llf.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.llf.enums.ErrorCode;
import com.llf.exception.BussException;

/**
 * 登录拦截器，校验是否登录
 *
 */
//@Component
//public class LoginChk implements HandlerInterceptor {
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws IOException, BussException {
//
//		if (isLogin(request,response)) {
//			return true;
//		} 
//		
//		return false;
//	}
//	
//	private boolean isLogin(HttpServletRequest request,HttpServletResponse response) throws BussException{
//		
//		String token = request.getHeader("token");
//		if(StringUtils.isEmpty(token)) {
//			throw new BussException(ErrorCode.USER_NO_LOGIN.getCode(), ErrorCode.USER_NO_LOGIN.getMsg());
//		}
//		
//		return true;
//	}
//}
