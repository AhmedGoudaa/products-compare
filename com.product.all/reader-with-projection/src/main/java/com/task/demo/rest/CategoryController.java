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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/category")
@Value
public class CategoryController {

	CategoryService categoryService;
	CategoryMapper categoryMapper;

	@GetMapping("category")
	public Mono<List<CategoryResponse>> getAllCategories() {
		return categoryService.findAll()
			.map(categoryMapper::mapToResponse);
	}

}
