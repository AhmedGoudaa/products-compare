package com.task.sources.producer.impl;

import java.util.List;
import java.util.Random;

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
@Qualifier("AMAZON")
public class AmazonSourceProducer implements SourceProducer<ProductMessage> {

	private final static List<String> PRODUCTS = List.of(
		"iphone 10",
		"iphone 10 ultra",
		"iphone 10 ultra pro",
		"iphone 9",
		"iphone 11",
		"iphone X",
		"mac book pro 2000",
		"mac book pro 2020",
		"mac book pro 2030",
		"samsung galaxy note 10",
		"samsung galaxy note 11",
		"samsung galaxy note 20",
		"samsung galaxy note 20 ultra"
	);

	protected final KafkaSender<Integer, ProductMessage> kafkaSender;

	public AmazonSourceProducer(KafkaConfig kafkaConfig) {

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
		return "product";
	}

	@Override
	public Flux<List<ProductMessage>> getStream() {

		Random random = new Random();

		return Flux.range(1, PRODUCTS.size())
			.map(i ->

				ProductMessage.newBuilder()
					.setCategoryId(1)
					.setSourceId(1)
					.setName(PRODUCTS.get(i))
					.setLogo(PRODUCTS.get(i) + "jpg")
					.setPrice(random.nextInt(10000 - 1000) + 1000)
					.build()
			).buffer(2);
	}
}
