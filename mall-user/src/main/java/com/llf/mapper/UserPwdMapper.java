package com.llf.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.llf.po.UserPwdPo;

@Mapper
public interface UserPwdMapper {
	
	// 新增
	void add(UserPwdPo userPwdPo);
	
	// 删除
	void del(UserPwdPo userPwdPo);
	
	// 修改
	void upd(UserPwdPo userPwdPo);

	// 查询
	UserPwdPo qry(UserPwdPo userPwdPo);
	
	// 登录查询
	Map<String,Object> pwdQry(Map<String,Object> map);

}
