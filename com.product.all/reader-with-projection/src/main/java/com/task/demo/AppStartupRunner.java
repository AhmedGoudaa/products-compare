package com.task.demo;

import com.task.demo.consumer.ProductConsumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@EnableScheduling
@Slf4j
public class AppStartupRunner implements ApplicationRunner {

	private final ProductConsumer consumer;

	// run kafka consumer on start up
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Disposable disposable = consumer.receiver();
	}
}
