package com.task.demo.service.impl;

import java.util.List;

import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;
import com.task.demo.repository.ProductWriteRepository;
import com.task.demo.service.ProductWriterService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.task.demo.config.AppConfig.BATCH_SCHEDULER;


@Service
public class ProductWriterServiceImpl implements ProductWriterService {
	private final ProductWriteRepository writeRepository;
	private final Scheduler bacthScheduler;

	ProductWriterServiceImpl(ProductWriteRepository writeRepository,
		@Qualifier(BATCH_SCHEDULER) Scheduler bacthScheduler) {
		this.writeRepository = writeRepository;
		this.bacthScheduler = bacthScheduler;
	}


	@Override
	public Mono<Integer> save(List<ProductMessage> products) {
		return Mono.fromCallable(() -> writeRepository.batchSave(products))
			.subscribeOn(bacthScheduler);
	}

	@Override
	public Mono<Integer> update(List<Product> products) {
		return Mono.fromCallable(() -> writeRepository.batchUpdate(products))
			.subscribeOn(bacthScheduler);
	}
}
