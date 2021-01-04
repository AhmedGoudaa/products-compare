package com.task.sources.producer.impl;

import java.util.List;

import com.task.demo.message.ProductMessage;
import com.task.sources.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Qualifier("EBAY")
public class EbaySourceProducer extends AbstractProducer<ProductMessage> {

	public EbaySourceProducer(KafkaConfig kafkaConfig) {
		super(kafkaConfig);
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
