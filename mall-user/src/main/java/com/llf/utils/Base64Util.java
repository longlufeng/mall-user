package com.llf.utils;

import java.util.Base64;

public class Base64Util {
	
	/**
	 * base64 加码
	 * @param text
	 * @return
	 */
	public static String encode(String text) {
		byte[] textBytes = text.getBytes();
		String encoded = Base64.getEncoder().encodeToString(textBytes);
		return encoded;
		
	}
	
	/**
	 * base64 解码
	 * @param text
	 * @return
	 */
	public static String decode(String encode) {
		byte[] decodeBytes = Base64.getDecoder().decode(encode);
		return new String(decodeBytes);
		
	}
	


	public static void main(String[] args) {
	    String data = "这是加密的内容";
	    System.out.println("加码前的数据："+data);
	    
	    String encodeData = encode(data);
	    System.out.println("加码后的数据："+encodeData);

	    String decodeData = decode(encodeData);
	    System.out.println("解码码后的数据："+decodeData);

	    //6L+Z5piv5Yqg5a+G55qE5YaF5a65
	    //6L+Z5piv5Yqg5a+G55qE5YaF5a65
	    //这是加密的内容

	}


}
