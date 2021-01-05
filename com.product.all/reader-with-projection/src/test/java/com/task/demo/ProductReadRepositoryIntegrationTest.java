package com.task.demo;

import com.task.demo.entity.Product;
import com.task.demo.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest

public class ProductReadRepositoryIntegrationTest {

	@Autowired
	ProductRepository repository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void products_should_not_be_empty() {
		Iterable<Product> products = repository.findAll();

		Assertions.assertThat(products).isNotEmpty();
	}

}
