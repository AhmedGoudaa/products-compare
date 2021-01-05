package com.task.demo.config;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	public static final String JDBC_SCHEDULER = "jdbc-Scheduler";
	public static final String BATCH_SCHEDULER = "batch-Scheduler";

	/**
	 *
	 * Number of threads = Number of Available Cores * Target CPU utilization * (1 + Wait time / Service time)"
	 * Waiting time (MS) - is the time spent waiting for IO bound tasks to complete, say waiting for HTTP response from remote service.
	 * Service time (MS) - is the time spent being busy, say processing the HTTP response, marshaling/unmarshalling,any other transformations etc.
	 *
	 * (Wait time / Service time) - this ratio is often called blocking coefficient.
	 *
	 * A computation-intensive task has a blocking coefficient close to 0, in this case,
	 * the number of threads is equal to the number of available cores.
	 * If all tasks are computation intensive,
	 * then this is all we need. Having more threads will not help.
	 *
	 */

	@Bean(name = JDBC_SCHEDULER)
	public Scheduler jdbcScheduler() {
		return Schedulers.newBoundedElastic(
			(int) (Runtime.getRuntime().availableProcessors() * 0.5 * (1 + 150 / 10)),
			10_000,
			JDBC_SCHEDULER
		);
	}

	@Bean(name = BATCH_SCHEDULER)
	public Scheduler batchScheduler() {
		return Schedulers.newBoundedElastic(
			(int) (Runtime.getRuntime().availableProcessors() * 0.5 * (1 + 150 / 10)),
			10_000,
			JDBC_SCHEDULER
		);
	}

}
