package com.jhub.message.ui.controller;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jhub.message.io.entity.MessageEntity;
import com.jhub.message.shared.constants.KafkaConstant;

@RestController
public class ChatController {

	@Autowired
	private KafkaTemplate<String, MessageEntity> kafkaTemplate;

	@PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
	public void sendMessage(@RequestBody MessageEntity message) {
		message.setTimestamp(LocalDateTime.now().toString());

		try {
            //Sending the message to kafka topic queue
			kafkaTemplate.send(KafkaConstant.KAFKA_TOPIC, message).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	// ----------------WebSocket API------------------------

	@MessageMapping("/sendMessage")
	@SendTo("/topic/group")

	public MessageEntity broadcastGroupMessage(@Payload MessageEntity message) {
		// sending this message to all the subscribe
		return message;
	}

	@MessageMapping("/new-user")
	@SendTo("/topic/group")
	public MessageEntity addUser(@Payload MessageEntity message, SimpMessageHeaderAccessor headerAccessor) {

		headerAccessor.getSessionAttributes().put("username", message.getSender());
		return message;

	}

}
