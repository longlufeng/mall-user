package com.llf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.UserPwdUpdDto;
import com.llf.service.UserPwdService;
import com.llf.utils.Result;

@RestController
@RequestMapping("/user")
public class UserPwdController {
	
	@Autowired
	UserPwdService userPwdService;
	
	@RequestMapping("/updPwd")
	public Result<?> updPwd(@RequestBody UserPwdUpdDto userPwdUpdDto,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		userPwdUpdDto.setUserId((String)session.getAttribute("userId"));
		userPwdService.updPwd(userPwdUpdDto);
		
		return Result.success();
	}
	
	@RequestMapping("/resetPwd")
	public Result<?> resetPwd(@RequestBody UserPwdUpdDto userPwdUpdDto,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		userPwdUpdDto.setUserId((String)session.getAttribute("userId"));
		userPwdService.resetPwd(userPwdUpdDto);
		
		return Result.success();
	}
	
}
