package com.ilongchat.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilongchat.modal.InMessage;
import com.ilongchat.util.GsonUtils;

import io.netty.channel.Channel;

public class LoginHandler implements IHandler{
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	@Override
	public void handlerMessage(Channel channel,InMessage message) {
		
		log.info("接收消息:{}",message);
		channel.writeAndFlush(GsonUtils.GsonString(message));
	}
	
}
