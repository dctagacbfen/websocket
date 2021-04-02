package com.ilongchat.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.ilongchat.comm.Constant;
import com.ilongchat.modal.InMessage;
import com.ilongchat.util.GsonUtils;
import com.ilongchat.websocket.Session;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
@Service
public class ActiveMqConsumer implements IConsumer{
	private static final Logger log = LoggerFactory.getLogger(ActiveMqConsumer.class);

	
	@JmsListener(destination = Constant.GAME_RESPONSE_TOPIC)
	public void receive(String message) {
		InMessage inMessage = GsonUtils.GsonToBean(message, InMessage.class);
		Map<String,Object> data = inMessage.getData();
		String uid = (String)data.get("uid");
		Channel channel = Session.getAttriube(uid);
		if(channel!=null){
			channel.writeAndFlush(new TextWebSocketFrame(message));
		}else{
			log.info("用户不在线消费消息失败！");
		}
		System.out.println("activeMq 收到消息{}"+message);
	}
}
