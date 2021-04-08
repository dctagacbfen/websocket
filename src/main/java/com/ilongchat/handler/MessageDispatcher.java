package com.ilongchat.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilongchat.comm.MessageType;
import com.ilongchat.modal.InMessage;

import io.netty.channel.Channel;

public class MessageDispatcher{
	
	private static final Logger log = LoggerFactory.getLogger(MessageDispatcher.class);
	private static Map<String,IHandler> handlerMap = new HashMap<String, IHandler>();
	
	static{
		handlerMap.put(MessageType.LOGIN, new LoginHandler());
		handlerMap.put(MessageType.POINT, new SendPointHandler());
		handlerMap.put(MessageType.MSG, new SendMsgHandler());
	}
	public static void dispatch(Channel channel,InMessage message){
		IHandler handler = handlerMap.get(message.getType());
		if(handler!=null){
			handler.handlerMessage(channel, message);
		}else{
			log.error("消息类型不正确,type={},handler={}",message.getType(),handlerMap);
			channel.close();
		}
	}
	
}
