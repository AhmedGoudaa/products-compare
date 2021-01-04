package com.task.demo.config;


import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;


	public <V extends Message> ReceiverOptions<Integer, V> consumerOptions() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "50");
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "500000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, "product-client");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "prodcut-consumer");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);

		return ReceiverOptions.create(props);
	}

}
