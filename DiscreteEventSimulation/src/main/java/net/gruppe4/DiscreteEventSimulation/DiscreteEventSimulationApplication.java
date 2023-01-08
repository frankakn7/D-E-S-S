package net.gruppe4.DiscreteEventSimulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiscreteEventSimulationApplication {

	//private static final Logger log = LoggerFactory.getLogger(DiscreteEventSimulationApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(DiscreteEventSimulationApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner demo(PlanRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Plan("Plan1"));
			repository.save(new Plan("Plan2"));
			repository.save(new Plan("Plan3"));
			repository.save(new Plan("Plan4"));

			// fetch all customers
			String savedUuid = "";
			log.info("Plans found with findAll():");
			log.info("-------------------------------");
			for (Plan plan : repository.findAll()) {
				log.info(plan.toString());
				savedUuid = plan.getUuid();
			}
			log.info("");

			// fetch an individual customer by ID
			Plan plan = repository.findByUuid(savedUuid);
			log.info("Customer found with findById("+savedUuid+"):");
			log.info("--------------------------------");
			log.info(plan.toString());
			log.info("");
		};
	}*/

}
