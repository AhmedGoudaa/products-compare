package com.task.demo.mapper;

import java.util.List;

import com.task.demo.dto.response.CategoryResponse;
import com.task.demo.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	List<CategoryResponse> mapToResponse(List<Category> categories);

	default CategoryResponse map(Category value) {
		CategoryResponse response = new CategoryResponse();

		return response.withId(value.getId())
			.withName(value.getName());
	}
}
