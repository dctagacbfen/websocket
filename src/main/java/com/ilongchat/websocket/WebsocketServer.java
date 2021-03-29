package com.ilongchat.websocket;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

@Component
public class WebsocketServer {

	private static final Logger log = LoggerFactory.getLogger(WebsocketServer.class);
	private final ChannelGroup bossGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	

	private Channel channel;
	
	public ChannelFuture start(InetSocketAddress adress) throws InterruptedException{
		ServerBootstrap bootStrap = new ServerBootstrap();
		bootStrap.group(workGroup).channel(NioServerSocketChannel.class).childHandler(new ChatServerInitializer());
		ChannelFuture futrue = bootStrap.bind(adress).sync();
		channel = futrue.channel();
		return futrue;
	}
	@PreDestroy
	public void destory(){
		log.debug("destory netty server");
		if(channel!=null){
			channel.close();
			bossGroup.close();
			workGroup.shutdownGracefully();
		}
	}
	@PostConstruct
	public void startServer() throws InterruptedException {
		log.info("netty服务器启动。。。。");
		start(new InetSocketAddress(8999));
		log.info("netty服务器启动完毕。。。。");
	}
}
