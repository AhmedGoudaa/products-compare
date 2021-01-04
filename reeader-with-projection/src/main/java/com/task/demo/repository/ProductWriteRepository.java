package com.task.demo.repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// TODO:: this Repository would be part of the data projection project

@Repository
public class ProductWriteRepository {
	private static final String INSERT_SQL =
		"""
			INSERT INTO  my_db.product (name, logo, price, category_id, source_id)
			VALUES (?, ?, ?, ?, ?)
			""";

	private static final String UPDATE_SQL =
		"UPDATE my_db.product set price = ?, updated_at = ? where id = ? ";

	private final JdbcTemplate jdbcTemplate;
	private final int batchSize;

	public ProductWriteRepository(JdbcTemplate jdbcTemplate,
		@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}") int batchSize) {
		this.jdbcTemplate = jdbcTemplate;
		this.batchSize = batchSize;
	}


	/**
	 * Batch save for new products.
	 *
	 * @param data
	 * @return size of saved data
	 */
	public int batchSave(List<ProductMessage> data) {

		for (int i = 0; i < data.size(); i += batchSize) {

			List<ProductMessage> batchList = data.subList(i, Math.min(i + batchSize, data.size()));


			jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement preparedStatement, int j)
					throws SQLException {


					var product = batchList.get(j);
					preparedStatement.setString(1, product.getName());
					preparedStatement.setString(2, product.getLogo());
					preparedStatement.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
					preparedStatement.setInt(4, product.getCategoryId());
					preparedStatement.setInt(5, product.getSourceId());

				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
		return data.size();
	}

	/**
	 * Batch update for prices of existing products.
	 *
	 * @param data
	 * @return size of update data
	 */
	public int batchUpdate(List<Product> data) {

		final Timestamp timestamp = new Timestamp((new Date()).getTime());

		for (int i = 0; i < data.size(); i += batchSize) {

			List<Product> batchList = data.subList(i, Math.min(i + batchSize, data.size()));


			jdbcTemplate.batchUpdate(UPDATE_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement preparedStatement, int j)
					throws SQLException {

					var product = batchList.get(j);
					preparedStatement.setBigDecimal(1, product.getPrice());
					preparedStatement.setTimestamp(2, timestamp);
					preparedStatement.setLong(3, product.getId());
				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
		return data.size();
	}

}
