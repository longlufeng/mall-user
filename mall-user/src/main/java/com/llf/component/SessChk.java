package com.llf.component;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.llf.enums.ErrorCode;
import com.llf.exception.BussException;

//@Component
//public class SessChk implements HandlerInterceptor{
//	
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
//
//	@Value("${sess.time-out}")
//	public String sessTimeOut;
//
//	@Value("${url.no-need-chk-session}")
//	public String noNeedChkSession;
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		
//		// 放行指定的uri
//		String reqUri  = request.getRequestURI();
//		if(noNeedChkSession.indexOf(reqUri) > -1) {
//			return HandlerInterceptor.super.preHandle(request, response, handler);
//		}
//		
//		// 获取会话信息
//		HttpSession session = request.getSession();
//		String sessionId = session.getId();
//		String userId = (String)session.getAttribute("userId");
//		
//		String RedisSssionId = (String) redisTemplate.opsForValue().get("userId:"+userId);
//		
//		if(StringUtils.isEmpty(sessionId) || StringUtils.isEmpty(RedisSssionId)) {
//			throw new BussException(ErrorCode.USER_SESS_OUT.getCode(), ErrorCode.USER_SESS_OUT.getMsg());
//		}
//		
//		if(!sessionId.equals(RedisSssionId)) {
//			throw new BussException(ErrorCode.USER_EXCH_DEV.getCode(), ErrorCode.USER_EXCH_DEV.getMsg());
//		}
//		
//		redisTemplate.opsForValue().set("userId:"+session.getAttribute("userId"), sessionId,Long.parseLong(sessTimeOut),TimeUnit.SECONDS);
//		
//		
//		return HandlerInterceptor.super.preHandle(request, response, handler);
//	}

//}
