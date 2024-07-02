package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPwdUpdDto {
	
	private String userId;
	private String oldPwd;
	private String newPwd;
	
}
