package com.llf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class Test {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
    @org.junit.jupiter.api.Test
    void testOne() {
        redisTemplate.opsForValue().set("name1","卷心菜");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name); //卷心菜
    }


}
