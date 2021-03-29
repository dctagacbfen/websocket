package com.ilongchat.websocket;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;

@Scope(value="prototype")
@Service
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri ="/ws";

	

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		if (wsUri.equalsIgnoreCase(msg.uri().substring(0, 3))) {
			String userId = findUserIdByUri(msg.uri());
			if (userId != null && userId.trim() != null && userId.trim().length() > 0) {

				ctx.channel().attr(AttributeKey.valueOf(ctx.channel().id().asShortText())).set(userId);// 写userid值
				Session.setAttribute(userId, ctx.channel());

			} else {
			} // 没有获取到用户Id
			ctx.fireChannelRead(msg.setUri(wsUri).retain());
		}
	}

	private String findUserIdByUri(String uri) {// 通过Uid获取用户Id--uri中包含userId
		String userId = "";
		try {
			userId = uri.substring(uri.indexOf("userId") + 7);
			if (userId != null && userId.trim() != null && userId.trim().length() > 0) {
				userId = userId.trim();
			}
		} catch (Exception e) {
		}
		return userId;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		cause.printStackTrace(System.err);
	}
}
