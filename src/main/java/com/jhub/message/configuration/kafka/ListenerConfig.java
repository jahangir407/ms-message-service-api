package com.jhub.message.configuration.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.jhub.message.io.entity.MessageEntity;
import com.jhub.message.shared.constants.KafkaConstant;

@EnableKafka
@Configuration
public class ListenerConfig {

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, MessageEntity> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, MessageEntity> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(null);

		return factory;
	}

	@Bean
	public ConsumerFactory<String, MessageEntity> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(),
				new JsonDeserializer<>(MessageEntity.class));
	}

	@Bean
	public Map<String, Object> consumerConfigurations() {
		Map<String, Object> configurations = new HashMap<>();

		configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstant.KAFKA_BROKER);
		configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstant.GROUP_ID);
		configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		return configurations;
	}

}
