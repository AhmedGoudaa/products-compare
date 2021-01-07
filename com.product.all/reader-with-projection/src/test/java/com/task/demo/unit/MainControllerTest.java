package com.task.demo.unit;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.task.demo.dto.response.ProductResponse;
import com.task.demo.entity.Category;
import com.task.demo.entity.Product;
import com.task.demo.entity.Source;
import com.task.demo.mapper.CategoryMapper;
import com.task.demo.mapper.ProductMapper;
import com.task.demo.mapper.ProductMapperImpl;
import com.task.demo.rest.ProductController;
import com.task.demo.service.CategoryService;
import com.task.demo.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ProductController.class)
@Import({ProductMapperImpl.class})
public class MainControllerTest {

	@MockBean
	ProductService productService;

	@Autowired
	private WebTestClient webClient;

	@Test
	public void test_get_product_by_name_and_category() {
		var product = (new Product())
			.withId(1L)
			.withName("Test")
			.withSource(new Source(1))
			.withCategory(new Category(1))
			.withPrice(BigDecimal.TEN);

		List<Product> products = Collections.singletonList(product);

		Mockito
			.when(productService.search(1, "Test"))
			.thenReturn(Mono.just(products));


		webClient.get().uri("/product/{name}/category/{id}",  "Test",1)
			.header(HttpHeaders.ACCEPT, "application/json")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(ProductResponse.class);

		Mockito.verify(productService, Mockito.times(1)).search(1, "Test");
	}

}
