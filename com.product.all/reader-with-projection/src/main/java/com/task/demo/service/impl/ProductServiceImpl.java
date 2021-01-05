package com.task.demo.service.impl;

import java.util.List;
import java.util.Optional;

import com.task.demo.entity.Product;
import com.task.demo.repository.ProductRepository;
import com.task.demo.service.ProductService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.task.demo.config.AppConfig.JDBC_SCHEDULER;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final Scheduler jdbcScheduler;

	ProductServiceImpl(ProductRepository productRepository,
		@Qualifier(JDBC_SCHEDULER) Scheduler jdbcScheduler) {
		this.productRepository = productRepository;
		this.jdbcScheduler = jdbcScheduler;
	}

	@Override
	public Mono<List<Product>> search(int categoryId, String name) {
		return Mono.fromCallable(() -> productRepository.findProductByNameAndCategory(name, categoryId))
			.subscribeOn(jdbcScheduler);
	}

	@Override
	public Mono<Optional<Product>> findOne(int categoryId, int sourceId, String name) {
		return Mono.fromCallable(() -> productRepository.findProductWithScore(name, categoryId, sourceId))
			.subscribeOn(jdbcScheduler);
	}
}
