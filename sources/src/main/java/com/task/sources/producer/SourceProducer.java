package com.task.sources.producer;

import java.util.List;
import java.util.stream.Collectors;

import com.task.demo.message.ProductMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

public interface SourceProducer<M extends ProductMessage> {


	/**
	 * kafka topic name.
	 * @return name.
	 */
	String getTopicName();


	/**
	 * The fetching and mapping logic of the source.
	 *
	 * @return publisher of List of proto messages
	 */
	Flux<List<M>> getStream();

	KafkaSender<Integer, M> kafkaSender();


	/**
	 * Fetch data from source and publish to kafka
	 * @return
	 */
	default Disposable fetchAndPublish() {

		// map the messages fetched from source to kafka records
		Flux<SenderRecord<Integer, M, Integer>> recordFlux =
			getStream()
				.flatMapIterable(messages -> messages.stream()
					.map(m -> SenderRecord
						.create(
							new ProducerRecord<>(getTopicName(), m.getCategoryId(), m), m.getCategoryId()
						)).collect(Collectors.toList())
				);

		return kafkaSender()
			.send(recordFlux)
			.subscribe();

	}

}
