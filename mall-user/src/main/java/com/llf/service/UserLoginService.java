package com.llf.service;

import java.util.Map;

import com.llf.dto.UserPwdAddDto;
import com.llf.dto.UserPwdQryDto;

public interface UserLoginService {
	
	// 注册
	void register(UserPwdAddDto userPwdAddDto) throws Exception;
	
	// 登录
	Map<String,Object> login(UserPwdQryDto userPwdQryDto) throws Exception;
	
}
