package com.task.sources;

import com.task.demo.message.ProductMessage;
import com.task.sources.producer.SourceProducer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Configuration
@EnableScheduling
public class AppStartupRunner implements ApplicationRunner {

	private final SourceProducer<ProductMessage> amazonSource;

	AppStartupRunner(
		@Qualifier("AMAZON") SourceProducer<ProductMessage> amazonSource
	) {
		this.amazonSource = amazonSource;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}


	/**
	 * fetch everyday at 10 AM .
	 *
	 */

	@Scheduled(cron = "0 0 10 * * ?", zone = "Asia/Dubai")
	public void fetch() {
		amazonSource.fetchAndPublish();
	}

}
