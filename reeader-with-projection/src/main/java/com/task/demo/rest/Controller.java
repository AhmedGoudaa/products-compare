package com.task.demo.rest;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.task.demo.dto.response.CategoryResponse;
import com.task.demo.dto.response.ProductResponse;
import com.task.demo.mapper.CategoryMapper;
import com.task.demo.mapper.ProductMapper;
import com.task.demo.service.CategoryService;
import com.task.demo.service.ProductService;
import lombok.Value;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@Value
public class Controller {

	CategoryService categoryService;
	CategoryMapper categoryMapper;
	ProductService productService;
	ProductMapper productMapper;


	@GetMapping("category")
	public Mono<List<CategoryResponse>> getAllCategories() {
		return categoryService.findAll()
			.map(categoryMapper::mapToResponse);
	}


	@GetMapping("category/{id}/product/{name}")
	public Mono<List<ProductResponse>> getProducts(
		@PathVariable  @Valid @NotNull Integer id,
		@PathVariable  @Valid @NotNull String name
	) {
		return productService.search(id, name)
			.map(productMapper::mapToResponse);
	}

}
