package com.task.sources.producer.impl;

import java.util.List;

import com.task.demo.message.ProductMessage;
import com.task.sources.config.DefaultKafkaSerializer;
import com.task.sources.config.KafkaConfig;
import com.task.sources.producer.SourceProducer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("EBAY")
public class EbaySourceProducer implements SourceProducer<ProductMessage> {

	protected final KafkaSender<Integer, ProductMessage> kafkaSender;

	public EbaySourceProducer(KafkaConfig kafkaConfig) {

		SenderOptions<Integer, ProductMessage> senderOptions =
			kafkaConfig
				.<ProductMessage>senderOptions()
				.withValueSerializer(new DefaultKafkaSerializer<>());
		this.kafkaSender = kafkaConfig.kafkaSender(senderOptions);
	}

	@Override
	public KafkaSender<Integer, ProductMessage> kafkaSender() {
		return kafkaSender;
	}

	@Override
	public String getTopicName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Flux<List<ProductMessage>> getStream() {
		throw new UnsupportedOperationException();
	}
}
