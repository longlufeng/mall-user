package com.llf.vo;

import com.alibaba.fastjson.support.spring.annotation.ResponseJSONP;

import lombok.Data;

@Data
@ResponseJSONP
public class UserPwdVo {
	
	private String userId;
	private String userName;
	private String userPassword;
	
}
