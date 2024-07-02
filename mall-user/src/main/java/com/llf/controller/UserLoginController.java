package com.llf.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.UserPwdAddDto;
import com.llf.dto.UserPwdQryDto;
import com.llf.service.UserLoginService;
import com.llf.utils.Result;


@RestController
@RequestMapping("/user")
public class UserLoginController {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${sess.time-out}")
	public String sessTimeOut;
	
	@RequestMapping("/register")
	public Result<?> register(@RequestBody UserPwdAddDto userPwdAddDto) throws Exception {
		
		userLoginService.register(userPwdAddDto);
		
		return Result.success();
	}
	
	@RequestMapping("/login")
	public Result<?> login(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 登录
		Map<String,Object> resMap = userLoginService.login(userPwdQryDto);
		
		// 设置会话，会将会话信息保存在redis
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(Integer.parseInt(sessTimeOut));
		session.setAttribute("userId", MapUtils.getString(resMap, "userId"));
		session.setAttribute("userName", MapUtils.getString(resMap, "userName"));
		session.setAttribute("certName", MapUtils.getString(resMap, "certName"));
		session.setAttribute("certNo", MapUtils.getString(resMap, "certNo"));
		session.setAttribute("addr", MapUtils.getString(resMap, "addr"));
		
		// 将sessionId保存在redis
		String sessionId = session.getId();
		redisTemplate.opsForValue().set("userId:"+MapUtils.getString(resMap, "userId"), sessionId,Long.parseLong(sessTimeOut),TimeUnit.SECONDS);
		
		String token = UUID.randomUUID().toString().replace("-", "");
		
		response.addHeader("token",token);
		
		return Result.success(resMap);
	}
	
}
