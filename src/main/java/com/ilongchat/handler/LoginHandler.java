package com.ilongchat.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilongchat.comm.Constant;
import com.ilongchat.modal.InMessage;
import com.ilongchat.modal.User;
import com.ilongchat.util.GsonUtils;
import com.ilongchat.websocket.Session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class LoginHandler implements IHandler{
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	@Override
	public void handlerMessage(Channel channel,InMessage message) {
		
		AttributeKey<User> userKey = AttributeKey.valueOf(Constant.SESSION_USER);
		User user = channel.attr(userKey).get();
		Session.setAttribute(user.getUserId(),channel);
		log.info("接收消息:{}",message);
		channel.writeAndFlush(GsonUtils.GsonString(message));
	}
	
}
