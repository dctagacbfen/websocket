package com.ilongchat.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel>  {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		
		ch.pipeline().addLast(new HttpServerCodec())
		.addLast(new ChunkedWriteHandler())
		.addLast(new HttpObjectAggregator(64*1024))
		.addLast(new WebSocketServerProtocolHandler("/ws"))
		.addLast(new TextWebSocketFrameHandler());
	}
	

}
