package com.task.demo.service;

import java.util.List;
import java.util.Optional;

import com.task.demo.entity.Product;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Pageable;

public interface ProductService {

	/**
	 * Search and return all result
	 * @param categoryId
	 * @param name
	 * @return
	 */
	Mono<List<Product>> search(int categoryId, String name);

	/**
	 * Search and return paginated result.
	 *
	 * @param categoryId
	 * @param name
	 * @param pageable
	 * @return
	 */
	Mono<List<Product>> search(int categoryId, String name, Pageable pageable);

	/**
	 * Find one
	 * @param categoryId
	 * @param sourceId
	 * @param name
	 * @return
	 */
	Mono<Optional<Product>> findOne(int categoryId, int sourceId, String name);

}
