package com.ilongchat.websocket;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;

public class Session {
	private static Map<String,Channel> userInfo = new HashMap<String,Channel>();

	public static Map<String, Channel> getUserInfo() {
		return userInfo;
	}

	public static void setUserInfo(Map<String, Channel> userInfo) {
		Session.userInfo = userInfo;
	}
	
	public static void setAttribute(String userId,Channel channel){
		userInfo.put(userId, channel);
	}
	public static Channel getAttriube(String userId){
		return userInfo.get(userId);
	}
	public static void removeAttribute(String userId){
		userInfo.remove(userId);
	}
	
}
