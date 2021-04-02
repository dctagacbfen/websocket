package com.ilongchat.websocket;

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
		.addLast(new HttpRequestHandler())
		.addLast(new WebSocketServerProtocolHandler("/ws"))
		.addLast(new TextWebSocketFrameHandler());
	}
	

}
