package com.task.demo.service;

import java.util.List;

import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;
import reactor.core.publisher.Mono;

public interface ProductWriterService {

	Mono<Integer> save(List<ProductMessage> products);

	Mono<Integer> update(List<Product> products);
}
