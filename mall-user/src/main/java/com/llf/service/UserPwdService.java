package com.llf.service;

import com.llf.dto.UserPwdUpdDto;

public interface UserPwdService {
	
	// 修改密码
	void updPwd(UserPwdUpdDto userPwdUpdDto);
	
	// 重置密码
	void resetPwd(UserPwdUpdDto userPwdUpdDto);

}
