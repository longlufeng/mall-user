package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPwdQryDto {
	
	private String userId;
	private String userName;
	private String userPassword;
	
}
