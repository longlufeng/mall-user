package com.llf.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.llf.dto.UserPwdUpdDto;
import com.llf.mapper.UserPwdMapper;
import com.llf.po.UserPwdPo;
import com.llf.service.UserPwdService;
import com.llf.utils.Base64Util;
import com.llf.utils.SM2Util;

@Service
public class UserPwdServiceImpl implements UserPwdService{
	
	@Autowired
	UserPwdMapper userPwdMapper;
	
	@Value("${sm2.privateKey}")
	String privateKey;
	
	@Override
	public void updPwd(UserPwdUpdDto userPwdUpdDto) {
		
		UserPwdPo userPwdPo = new UserPwdPo();
		userPwdPo.setUserId(userPwdUpdDto.getUserId());
		String userPassword = SM2Util.decrypt(Base64Util.decode(userPwdUpdDto.getNewPwd()), privateKey);
		userPwdPo.setUserPassword(userPassword);
		BeanUtils.copyProperties(userPwdUpdDto, userPwdPo);
		userPwdMapper.upd(userPwdPo);
		
	}

	@Override
	public void resetPwd(UserPwdUpdDto userPwdUpdDto) {
		
		UserPwdPo userPwdPo = new UserPwdPo();
		userPwdPo.setUserId(userPwdUpdDto.getUserId());
		String userPassword = SM2Util.decrypt(Base64Util.decode(userPwdUpdDto.getNewPwd()), privateKey);
		userPwdPo.setUserPassword(userPassword);
		BeanUtils.copyProperties(userPwdUpdDto, userPwdPo);
		userPwdMapper.upd(userPwdPo);
		
	}

}
