package com.task.demo.integration;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest

public class ProductReadRepositoryIntegrationTest {

//	@Autowired
//	ProductRe repository;
	@Autowired
	private TestEntityManager entityManager;

}
