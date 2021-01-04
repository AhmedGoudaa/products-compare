package com.task.demo.service;

import java.util.List;
import java.util.Optional;

import com.task.demo.entity.Product;
import reactor.core.publisher.Mono;

public interface ProductService {

	Mono<List<Product>> search(int categoryId, String name);

	Mono<Optional<Product>> findOne(int categoryId, int sourceId, String name);

}
