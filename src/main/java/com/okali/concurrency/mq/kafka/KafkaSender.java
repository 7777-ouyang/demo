package com.okali.concurrency.mq.kafka;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okali.concurrency.mq.Message;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送端
 * @author OKali
 *
 */
@Slf4j
@Component
public class KafkaSender {
	
	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private Gson gson = new GsonBuilder().create();
	
	public void send(String msg) {
		Message message = new Message();
		message.setId(System.currentTimeMillis());
		message.setMsg(msg);
		message.setSendTime(new Date());
		log.info("send message:{}", message);
		kafkaTemplate.send(TopicConstants.TEST, gson.toJson(message));
	}

}
