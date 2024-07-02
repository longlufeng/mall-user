package com.llf.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.UserPwdQryDto;
import com.llf.utils.Result;

@RestController
@RequestMapping("/test")
public class TestSessionRedisController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/setSession")
	public Result<?> setSession(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request) throws Exception{
		
		Map<String,Object> map = new HashMap<>();
		
		String sessionId = request.getSession().getId();
		
		request.getSession().setAttribute("userName", userPwdQryDto.getUserName());
		request.getSession().setAttribute("userPassword", userPwdQryDto.getUserPassword());
		
		
		map.put("sessionId", sessionId);
		
		map.put("userName", userPwdQryDto.getUserName());
		
		return Result.success(map);
	}
	
	@RequestMapping("/getSession")
	public Result<?> getSession(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request) throws Exception{
		
		String userName = (String) request.getSession().getAttribute("userName");
		
		return Result.success(userName);
	}
	
	@RequestMapping("/setRedis")
	public Result<?> setRedis(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		redisTemplate.opsForValue().set("nick","xiaosan",100,TimeUnit.SECONDS);
		
		return Result.success();
	}
	
	@RequestMapping("/getRedis")
	public Result<?> getRedis(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String resStr = (String) redisTemplate.opsForValue().get("nick");
		
		return Result.success(resStr);
	}
	
	@RequestMapping("/setRedisHash")
	public Result<?> setRedisHash(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		redisTemplate.opsForHash().put("hashValue","map1","map1-1");
		
		return Result.success();
	}
	
	@RequestMapping("/getRedisHash")
	public Result<?> getRedisHash(@RequestBody UserPwdQryDto userPwdQryDto,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String rsStr = (String) redisTemplate.opsForHash().get("hashValue","map1");
		
		return Result.success(rsStr);
		
		
	}
	
}
