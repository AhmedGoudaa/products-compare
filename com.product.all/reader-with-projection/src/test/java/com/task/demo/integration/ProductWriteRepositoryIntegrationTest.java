package com.task.demo.integration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.task.demo.entity.Product;
import com.task.demo.message.ProductMessage;
import com.task.demo.repository.CategoryRepository;
import com.task.demo.repository.ProductRepository;
import com.task.demo.repository.ProductWriteRepository;
import com.task.demo.repository.SourceRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductWriteRepositoryIntegrationTest {

	@ClassRule
	public static MysqlBaseContainer mysqlBaseContainer = MysqlBaseContainer.getInstance();

	@Autowired
	ProductRepository readRepository;

	@Autowired
	ProductWriteRepository repository;


	@Test
	public void products_batch_save() {
		List<ProductMessage> productMessages = Stream.of(1, 2, 3, 4)
			.map(v -> ProductMessage.newBuilder().setName("galaxy " + v).setPrice(v * 100).setSourceId(1)
				.setCategoryId(1).setLogo("l.png").build())
			.collect(Collectors.toList());

		int saved = repository.batchSave(productMessages);
		assertThat(saved).isEqualTo(4);

		List<Product> products = readRepository.findProductByNameAndCategory("galaxy", 1);

		assertThat(products).isNotEmpty();
		assertThat(products).size().isEqualTo(4);
	}

	@Test
	public void products_batch_update() {
		List<ProductMessage> productMessages = Stream.of(1, 2, 3, 4)
			.map(v -> ProductMessage.newBuilder().setName("productX " + v).setPrice(v * 100).setSourceId(1)
				.setCategoryId(1).setLogo("x.png").build())
			.collect(Collectors.toList());

		 repository.batchSave(productMessages);
		List<Product> products = readRepository.findProductByNameAndCategory("productX", 1);

		assertThat(products).isNotEmpty();
		assertThat(products).size().isEqualTo(4);

		products.forEach(product -> product.setPrice(BigDecimal.TEN));

		repository.batchUpdate(products);

		List<Product> newProducts = readRepository.findProductByNameAndCategory("productX", 1);

		assertThat(newProducts).allMatch(product -> product.getPrice().compareTo(BigDecimal.TEN) == 0);

	}



}
