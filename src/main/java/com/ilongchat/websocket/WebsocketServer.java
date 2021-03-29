package com.ilongchat.websocket;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class WebsocketServer {

	private final ChannelGroup bossGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	

	private Channel channel;
	
	public ChannelFuture start(InetSocketAddress adress) throws InterruptedException{
		ServerBootstrap bootStrap = new ServerBootstrap();
		bootStrap.group(workGroup).channel(NioServerSocketChannel.class).childHandler(new ChatServerInitializer(bossGroup));
		ChannelFuture futrue = bootStrap.bind(adress).sync();
		channel = futrue.channel();
		return futrue;
	}
	
	public void destory(){
		if(channel!=null){
			channel.close();
			bossGroup.close();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		final WebsocketServer server = new WebsocketServer();
		ChannelFuture futrue = server.start(new InetSocketAddress(8999));
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				server.destory();
			}
		}));
		futrue.channel().closeFuture().syncUninterruptibly();
	}
}
