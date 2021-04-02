package com.ilongchat.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilongchat.comm.Constant;
import com.ilongchat.handler.MessageDispatcher;
import com.ilongchat.modal.InMessage;
import com.ilongchat.modal.User;
import com.ilongchat.util.GsonUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	 
	private static final Logger log = LoggerFactory.getLogger(TextWebSocketFrame.class);
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
	    	AttributeKey<User> userKey = AttributeKey.valueOf(Constant.SESSION_USER);
			Attribute<User> attr = ctx.attr(userKey);
	        String userId = attr.get().getUserId();
	        Session.removeAttribute(userId);// 删除缓存的通道
	        super.channelInactive(ctx);
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
		protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
			String msgStr = frame.text();
			log.debug("netty收到客户端消息："+msgStr);
			InMessage msg = GsonUtils.GsonToBean(msgStr, InMessage.class);
			MessageDispatcher.dispatch(ctx.channel(), msg);
		}

}
