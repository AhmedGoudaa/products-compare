package com.task.demo.repository;

import java.util.List;
import java.util.Optional;

import com.task.demo.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(
		value = """
				SELECT * FROM product
				WHERE category_id = :categoryId AND
				MATCH(name) AGAINST(:name IN NATURAL LANGUAGE MODE)
				ORDER BY price ASC
			"""
		, nativeQuery = true)
	List<Product> findProductByNameAndCategory(final String name, final int categoryId);

	@Query(
		value = """
				SELECT * FROM product
				WHERE category_id = :categoryId AND
				MATCH(name) AGAINST(:name IN NATURAL LANGUAGE MODE)
				ORDER BY price ASC
			"""
		,countQuery ="""
				SELECT COUNT(1) FROM product
				WHERE category_id = :categoryId AND
				MATCH(name) AGAINST(:name IN NATURAL LANGUAGE MODE)
				ORDER BY price ASC
			"""
		, nativeQuery = true)
	List<Product> findProductByNameAndCategory(final String name, final int categoryId, Pageable pageable);


	@Query(
		value = """
				SELECT id, name, logo, price, updated_at, category_id, source_id,
			    MATCH (name)  AGAINST(:name IN NATURAL LANGUAGE MODE) as score 
			    FROM product
				WHERE category_id = :categoryId 
				AND source_id = :sourceId 
				AND MATCH(name) AGAINST(:name IN NATURAL LANGUAGE MODE)
				ORDER BY score limit 1
			"""
		, nativeQuery = true)
	Optional<Product> findProductWithScore(final String name, final int categoryId, final int sourceId);
}
