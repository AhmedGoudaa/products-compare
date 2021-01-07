package com.task.demo.consumer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import com.task.demo.config.DefaultKafkaDeserializer;
import com.task.demo.config.KafkaConfig;
import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;
import com.task.demo.service.ProductService;
import com.task.demo.service.ProductWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SynchronousSink;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverPartition;
import reactor.kafka.receiver.ReceiverRecord;

import org.springframework.stereotype.Service;

@Slf4j
//@AllArgsConstructor
@Service
public class ProductConsumer {
	private final ProductWriterService productWriterService;
	private final ProductService productReadService;
	private final KafkaConfig kafkaConfig;

	// we will have 2 different subscribers one for new product and the other is for the updated one
	private final Sinks.Many<ProductMessage> newProductSource;
	private final Sinks.Many<Product> updateProductSource;

	ProductConsumer(ProductWriterService productWriterService, ProductService productReadService, KafkaConfig kafkaConfig) {
		this.productWriterService = productWriterService;
		this.productReadService = productReadService;
		this.kafkaConfig = kafkaConfig;
		newProductSource = Sinks.many().unicast().onBackpressureBuffer();
		updateProductSource = Sinks.many().unicast().onBackpressureBuffer();
		initNewProductSink();
		initUpdatedProductSink();
	}

	public Disposable receiver() {
		ReceiverOptions<Integer, ProductMessage> integerMessageReceiverOptions =
			kafkaConfig.consumerOptions();

		integerMessageReceiverOptions = integerMessageReceiverOptions
			.addAssignListener(partitions -> partitions.forEach(ReceiverPartition::seekToEnd))
			.commitInterval(Duration.ofSeconds(1))
			.commitBatchSize(100)
			.withValueDeserializer(new DefaultKafkaDeserializer<ProductMessage>(ProductMessage.parser()))
			.subscription(List.of("product"));


		return KafkaReceiver.create(integerMessageReceiverOptions)
			.receive()
			.groupBy(ConsumerRecord::partition)
			.flatMap(partitionFlux ->
				partitionFlux
					.concatMap(record ->
					{
						final var productMessage = record.value();

						return productReadService
							.findOne(productMessage.getCategoryId(), productMessage.getSourceId(), productMessage
								.getName())
							.handle((Optional<Product> optionalProduct, SynchronousSink<ReceiverRecord<Integer, ProductMessage>> synchronousSink) -> {

								// if the product already exits send it to the  updateProductSource publisher to
								// be batch updated
								// else send it to  new produce publisher to be batch saved
								optionalProduct.ifPresentOrElse(product -> {
									BigDecimal price = BigDecimal
										.valueOf(productMessage.getPrice());
									if (product.getPrice().equals(price)) {
										// updated product
										product.setPrice(price);
										updateProductSource.emitNext(product, (signalType, emitResult) -> {
											if (emitResult.isSuccess()) {
												synchronousSink.next(record);
											}
											return false;
										});

									}

								}, () -> {
									// new product that will be saved
									newProductSource.emitNext(productMessage, (signalType, emitResult) -> {
										if (emitResult.isSuccess()) {
											synchronousSink.next(record);
										}
										return false;
									});

								});
							});
					})
			)
			.subscribe(voidVal -> {
				// do nothing
			}, throwable -> log.error("Error ==> ", throwable));

	}

	private void initUpdatedProductSink() {
		updateProductSource
			.asFlux()
			.buffer(100)
			.flatMap(productWriterService::update)
			.doOnNext(updatedCount -> log.info("updated count {}", updatedCount))
			.subscribe();
	}

	private void initNewProductSink() {
		newProductSource
			.asFlux()
			.buffer(100)
			.flatMap(productWriterService::save)
			.doOnNext(newCount -> log.info("new product count {}", newCount))
			.subscribe();
	}

}
