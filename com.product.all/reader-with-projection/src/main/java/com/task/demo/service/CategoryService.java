package com.task.demo.service;

import java.util.List;

import com.task.demo.entity.Category;
import reactor.core.publisher.Mono;

public interface CategoryService {

	Mono<List<Category>> findAll();
}
