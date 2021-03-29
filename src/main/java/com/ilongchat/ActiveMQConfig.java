package com.ilongchat;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ilongchat.comm.Constant;

@Configuration
public class ActiveMQConfig {  
  
    @Bean
    public Topic topic(){
    	return new ActiveMQTopic(Constant.GAME_REQUEST_TOPIC);
    }
	
} 
