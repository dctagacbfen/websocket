package com.ilongchat.handler;


import com.ilongchat.modal.InMessage;

import io.netty.channel.Channel;

public interface IHandler {
	
	public void handlerMessage(Channel channel,InMessage message);
}
