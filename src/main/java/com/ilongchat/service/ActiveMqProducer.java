package com.ilongchat.service;

import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
@Component
public class ActiveMqProducer implements IProducer{

	@Autowired
	private JmsMessagingTemplate  jmsTemplate;
	@Autowired
	private Topic topic;
	
	public void sendMessage(String message){
		System.err.println("sendMessage 发送消息{}"+message);
		jmsTemplate.convertAndSend(topic,message);
	}
}
