package com.llf.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.llf.dto.UserPwdAddDto;
import com.llf.dto.UserPwdQryDto;
import com.llf.enums.ErrorCode;
import com.llf.exception.BussException;
import com.llf.mapper.UserPwdMapper;
import com.llf.po.UserPwdPo;
import com.llf.service.UserLoginService;
import com.llf.utils.Base64Util;
import com.llf.utils.GenerateStrUtil;
import com.llf.utils.SM2Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserLoginServiceImpl implements UserLoginService{
	
	@Autowired
	UserPwdMapper userPwdMapper;
	
	@Value("${sm2.privateKey}")
	String privateKey;
	
	
	@Override
	public void register(UserPwdAddDto userPwdAddDto) throws BussException {
		
		String userName = userPwdAddDto.getUserName();
		
		UserPwdPo userPwdPo = new UserPwdPo();
		userPwdPo.setUserName(userName);
		UserPwdPo userPwdPoQry = userPwdMapper.qry(userPwdPo);
		if(userPwdPoQry != null) {
			log.info("鐢ㄦ埛銆恵}銆戝凡缁忚娉ㄥ唽",userName);
			throw new BussException(ErrorCode.USER_REGISTERD.getCode(), ErrorCode.USER_REGISTERD.getMsg());
		}
		
		// 瀵嗙爜base64瑙ｅ瘑
		String userPassword = Base64Util.decode(userPwdAddDto.getUserPassword());
		// 瀵嗙爜SM2瑙ｅ瘑
		userPassword = SM2Util.decrypt(userPassword, privateKey);
		log.info("userPassword涓簕}锛?, userPassword");
		userPwdPo.setUserId(GenerateStrUtil.generateUserId());
		userPwdPo.setUserPassword(userPassword);
		
		userPwdMapper.add(userPwdPo);
		
	}

	
	@Override
	public Map<String,Object> login(UserPwdQryDto userPwdQryDto) throws Exception {
		
		Map<String,Object> paramMap = new HashMap<>();
		
		String userPassword = SM2Util.decrypt(Base64Util.decode(userPwdQryDto.getUserPassword()), privateKey);
//		String userPassword = userPwdQryDto.getUserPassword();
		paramMap.put("userName", userPwdQryDto.getUserName());
		paramMap.put("userPassword", userPassword);
		
		Map<String,Object> resMap = userPwdMapper.pwdQry(paramMap);
		
		if(resMap == null) {
			throw new BussException(ErrorCode.USER_OR_PWD_ERROR.getCode(), ErrorCode.USER_OR_PWD_ERROR.getMsg());
		}
		
		return resMap;
	}

}
