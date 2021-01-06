package com.task.demo.integration;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class MysqlBaseContainer extends MySQLContainer<MysqlBaseContainer> {

//	static final MySQLContainer DATABASE = new MySQLContainer(DockerImageName.parse("mysql:8.0.22"))
//		.withDatabaseName("my_db");
//
//	static {
//		DATABASE.start();
//	}

	private static final String IMAGE_VERSION = "mysql:8.0.22";
	private static MysqlBaseContainer container;

	private MysqlBaseContainer() {
		super(IMAGE_VERSION);
	}

	public static MysqlBaseContainer getInstance() {
		if (container == null) {
			container = new MysqlBaseContainer().withDatabaseName("my_db");
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_URL", container.getJdbcUrl());
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
	}

	@Override
	public void stop() {
		//do nothing, JVM handles shut down
	}

//
//	@DynamicPropertySource
//	static void databaseProperties(DynamicPropertyRegistry registry) {
////		registry.add("spring.datasource.url", () -> "192.168.99.101");
//		System.out.println("HOSSSTTTT===>  "+DATABASE.getHost());
//		registry.add("spring.datasource.url", () -> "jdbc:mysql://192.168.99.101:55002/my_db");
//		registry.add("spring.datasource.url",DATABASE::getJdbcUrl);
//		registry.add("spring.datasource.username", DATABASE::getUsername);
//		registry.add("spring.datasource.password", DATABASE::getPassword);
//	}

}
