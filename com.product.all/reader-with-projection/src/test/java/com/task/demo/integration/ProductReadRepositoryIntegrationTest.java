package com.task.demo.integration;

import java.util.List;
import java.util.Optional;

import com.task.demo.entity.Product;
import com.task.demo.repository.CategoryRepository;
import com.task.demo.repository.ProductRepository;
import com.task.demo.repository.SourceRepository;
import org.assertj.core.api.Assertions;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductReadRepositoryIntegrationTest  {

	@ClassRule
	public static MysqlBaseContainer mysqlBaseContainer = MysqlBaseContainer.getInstance();

	@Autowired
	ProductRepository repository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	SourceRepository sourceRepository;

	@Test
	public void products_should_not_be_empty() {
		Iterable<Product> products = repository.findAll();
		assertThat(products).isNotEmpty();
	}

	@Test
	public void products_search_by_name_and_cat_should_not_be_empty() {

		List<Product> products = repository.findProductByNameAndCategory("note", 1);
		assertThat(products).isNotEmpty();
		assertThat(products).size().isEqualTo(2);
	}
	@Test
	public void product_search_by_name_and_cat_and_source_should_not_be_empty() {
		Optional<Product> productWithScore = repository.findProductWithScore("note 20", 1, 1);
		assertThat(productWithScore).isNotEmpty();
	}


}
