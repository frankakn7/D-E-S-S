package net.gruppe4.DiscreteEventSimulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class DiscreteEventSimulationApplication {

	//private static final Logger log = LoggerFactory.getLogger(DiscreteEventSimulationApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DiscreteEventSimulationApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Async-Sim-Executor-");
		executor.initialize();
		return executor;
	}

}
