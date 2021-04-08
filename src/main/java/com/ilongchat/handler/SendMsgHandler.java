package com.ilongchat.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilongchat.comm.Constant;
import com.ilongchat.modal.InMessage;
import com.ilongchat.modal.User;
import com.ilongchat.service.IProducer;
import com.ilongchat.util.GsonUtils;
import com.ilongchat.util.SpringUtil;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class SendMsgHandler implements IHandler{
	
	private static final Logger log = LoggerFactory.getLogger(SendPointHandler.class);
	private IProducer producer = SpringUtil.getBean(IProducer.class);
	
	@Override
	public void handlerMessage(Channel channel, InMessage message) {
		
		log.info("接收到普通消息:{}",message);
		
		AttributeKey<User> userKey = AttributeKey.valueOf(Constant.SESSION_USER);
		User user = channel.attr(userKey).get();
		
		Map<String,Object> data = message.getData();
		data.put("uid", user.getUserId());
		data.put("uname",user.getNickName());
		data.put("roomId",user.getRoomId());
		
		producer.sendMessage(GsonUtils.GsonString(message));
		
	}

}
