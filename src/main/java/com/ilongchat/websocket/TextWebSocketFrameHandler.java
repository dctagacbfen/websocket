package com.ilongchat.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	 
	    public TextWebSocketFrameHandler() {
	        super();
	    }
	 
	    @Override
	    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
	        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
	            ctx.pipeline().remove(HttpRequestHandler.class);
	            // group.writeAndFlush("");
	        } else {
	            super.userEventTriggered(ctx, evt);
	        }
	    }
	
	 
	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        ctx.close();
	        cause.printStackTrace();
	    }
	 
	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
	    	System.out.println("......channelInactive");
	        Channel incoming = ctx.channel();
	        String userId = (String) incoming.attr(AttributeKey.valueOf(incoming.id().asShortText())).get();
	        Session.removeAttribute(userId);// 删除缓存的通道
	    }

		@Override
		public boolean acceptInboundMessage(Object msg) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("......acceptInboundMessage");
			return super.acceptInboundMessage(msg);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("......channelRead");
			super.channelRead(ctx, msg);
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("......channelActive");
			super.channelActive(ctx);
		}

		@Override
		public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("......disconnect");
			super.disconnect(ctx, promise);
		}

		@Override
		protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
			ctx.write(msg.retain());
			
		}

}
