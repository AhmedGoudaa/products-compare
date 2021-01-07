package com.task.demo.rest;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.task.demo.dto.response.CategoryResponse;
import com.task.demo.dto.response.ProductResponse;
import com.task.demo.mapper.CategoryMapper;
import com.task.demo.mapper.ProductMapper;
import com.task.demo.service.CategoryService;
import com.task.demo.service.ProductService;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
@Value
public class ProductController {

	ProductService productService;
	ProductMapper productMapper;

	@GetMapping("/{name}/category/{id}")
	public Mono<List<ProductResponse>> getProducts(
		@PathVariable @Valid @NotNull Integer id,
		@PathVariable @Valid @NotNull String name
	) {
		return productService.search(id, name)
			.map(productMapper::mapToResponse);
	}

	@GetMapping("/paginated/{name}/category/{id}")
	public Mono<List<ProductResponse>> getProducts(
		@PathVariable @Valid @NotNull Integer id,
		@PathVariable @Valid @NotNull String name,
		@RequestParam(required = false, defaultValue = "1")  @Valid @Range(min = 1 ,max = 100) Integer pageNo,
		@RequestParam(required = false, defaultValue = "50") @Valid @Min(10) Integer pageSize
	) {
		return productService.search(id, name,PageRequest.of(pageNo,pageSize))
			.map(productMapper::mapToResponse);
	}

}
