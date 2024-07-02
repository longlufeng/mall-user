package com.llf.utils;

public class GenerateStrUtil {
	
	public static String generateUserId() {
	
		String userId = (int) ((Math.random() * 9 + 1) * 100000) + "";
		return userId;
		
	}
	
	public static void main(String[] args) {
		System.out.println(generateUserId());
	}

}
