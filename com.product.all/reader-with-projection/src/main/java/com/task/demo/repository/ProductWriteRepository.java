package com.task.demo.repository;

import java.util.List;

import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;

// TODO:: this Repository would be part of the data projection project

public interface ProductWriteRepository {


	/**
	 * Batch save for new products.
	 *
	 * @param data
	 * @return size of saved data
	 */
	int batchSave(List<ProductMessage> data);

	/**
	 * Batch update for prices of existing products.
	 *
	 * @param data
	 * @return size of update data
	 */
	int batchUpdate(List<Product> data);

}
