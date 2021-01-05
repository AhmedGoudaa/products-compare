package com.task.demo.mapper;

import java.util.List;

import com.task.demo.dto.response.ProductResponse;
import com.task.demo.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	List<ProductResponse> mapToResponse(List<Product> values);

	default ProductResponse map(Product value) {
		var response = new ProductResponse();

		return response.withName(value.getName())
			.withCategoryId(value.getCategory().getId())
			.withLogo(value.getLogo())
			.withPrice(value.getPrice())
			.withSourceId(value.getSource().getId())
			.withUpdatedAt(value.getUpdatedAt());
	}
}
