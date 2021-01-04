package com.task.sources.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String[] bootstrapAddress;


	public <V> SenderOptions<Integer, V> senderOptions() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.LINGER_MS_CONFIG, "100");
		config.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
		config.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "3145728"); // 3 MB
//		config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip"); //

		// 1 day
		config.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");
		config.put(TopicConfig.SEGMENT_MS_CONFIG, "86400000");

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.join(",", bootstrapAddress));
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);

		//At-most-once delivery
		return SenderOptions.<Integer, V>create(config)
			.producerProperty(ProducerConfig.ACKS_CONFIG, "0")
			.producerProperty(ProducerConfig.RETRIES_CONFIG, "0")
			.stopOnError(false);
	}


	public <V> KafkaSender<Integer, V> kafkaSender(SenderOptions<Integer, V> senderOptions) {
		return KafkaSender.create(senderOptions);
	}

}
