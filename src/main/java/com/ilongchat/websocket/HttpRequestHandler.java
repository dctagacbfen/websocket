package com.ilongchat.websocket;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ilongchat.comm.Constant;
import com.ilongchat.modal.InMessage;
import com.ilongchat.modal.User;
import com.ilongchat.util.GsonUtils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri ="/ws";
	
	private static final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);
	

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		log.debug("HttpRequestHandler messageReceived");
		if (wsUri.equalsIgnoreCase(msg.uri().substring(0, 3))) {
			log.debug("HttpRequestHandler msg uri"+msg.uri());
			setSession(ctx.channel(),msg.uri());
			ctx.fireChannelRead(msg.setUri(wsUri).retain());
		}
	}

	private void setSession(Channel channel,String uri) {// 通过Uid获取用户Id--uri中包含userId
		Map<String,String> paramMap = new HashMap<String,String>();
		try {
			String params  = uri.substring(uri.indexOf("?")+1);
			if(!StringUtils.isEmpty(params)){
				String param[] = params.split("&");
				for(String p:param){
					paramMap.put(p.split("=")[0],p.split("=")[1]);
				}
				String uid = paramMap.get("uid");
				String nickName = paramMap.get("uname");
				String roomId = paramMap.get("rid");
			    if(StringUtils.isEmpty(Session.getAttriube(uid))){
			    	Session.setAttribute(uid, channel);
					AttributeKey<User> userKey = AttributeKey.valueOf(Constant.SESSION_USER);
					Attribute<User> attr = channel.attr(userKey);
					User user = new User();
					user.setUserId(uid);
					user.setNickName(nickName);
					user.setRoomId(roomId);
					attr.setIfAbsent(user);
			    }
			}
		} catch (Exception e) {
			log.error("参数解析错误：",e);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace(System.err);
	}
}
