package com.jhub.message.configuration.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.jhub.message.io.entity.MessageEntity;
import com.jhub.message.shared.constants.KafkaConstant;

@Component
public class MessageListener {

	@Autowired
	SimpMessagingTemplate template;

	@KafkaListener(topics = KafkaConstant.KAFKA_TOPIC, groupId = KafkaConstant.GROUP_ID)
	public void listen(MessageEntity message) {
		System.out.println("sending via kafka listener...");
		template.convertAndSend("/topic/group", message);

	}

}
