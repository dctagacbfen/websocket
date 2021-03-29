package com.ilongchat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ilongchat.comm.Constant;
@Service
public class ActiveMqConsumer implements IConsumer{
	private static final Logger log = LoggerFactory.getLogger(ActiveMqConsumer.class);

	
	@JmsListener(destination = Constant.GAME_REQUEST_TOPIC)
	public void receive(String message) {
		
		System.out.println("activeMq 收到消息{}"+message);
	}
}
