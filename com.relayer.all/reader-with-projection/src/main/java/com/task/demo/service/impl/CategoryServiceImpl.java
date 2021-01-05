package com.task.demo.service.impl;

import java.util.List;

import com.task.demo.entity.Category;
import com.task.demo.repository.CategoryRepository;
import com.task.demo.service.CategoryService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.task.demo.config.AppConfig.JDBC_SCHEDULER;

@Service
public class CategoryServiceImpl implements CategoryService {


	private final CategoryRepository categoryRepository;
	private final Scheduler jdbcScheduler;

	CategoryServiceImpl(CategoryRepository categoryRepository,
		@Qualifier(JDBC_SCHEDULER) Scheduler jdbcScheduler) {
		this.categoryRepository = categoryRepository;
		this.jdbcScheduler = jdbcScheduler;
	}

	@Override
	public Mono<List<Category>> findAll() {
		return Mono.fromCallable(categoryRepository::findAll)
			.subscribeOn(jdbcScheduler);
	}
}
