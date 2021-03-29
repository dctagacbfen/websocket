package com.ilongchat.websocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;
@RunWith(SpringRunner.class)
@SpringBootTest
public class JmsTest{
	private static final Logger log = LoggerFactory.getLogger(JmsTest.class);


	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void testAdd(){
		
		stringRedisTemplate.opsForValue().set("aaa", "111");
		System.out.println(stringRedisTemplate.opsForValue().get("aaa"));

		
	}
	@Test
	public void testRedisSet(){
		stringRedisTemplate.opsForValue().set("aaa", "bbb");
	}
	@Test
	public void testGetRedis(){
		System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
	}
}
