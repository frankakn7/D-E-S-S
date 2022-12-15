package com.project.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationController {

	private final AtomicLong counter = new AtomicLong();

	@PostMapping("/plan/upload")
	public boolean upload() {
		Plan plan = new Plan(counter.incrementAndGet());
		return true;
	}

	@PostMapping("/plan/start/{planID}")
	public long startSimulationUsingPlan(@pathVariable("planID") long planID) {
		Simulation sim = new Simulation(planID);
		return sim.getId(); 
	}

	@GetMapping("/sim/status/{simID}")
	public String simulationStatusCheck(@pathVariable("simID") long simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/server/status")
	public long serverStatusCheck() {
		return 0;
	}

	@GetMapping("/sim/results")
	public long[] getResults(@RequestParam(value = "limit", defaultValue = "1") int limit,@RequestParam(String[] simID) String[] simID) { 
		return new long[1];
	}

	@DeleteMapping("/sim")
	public String deleteSimulations(@RequestParam(String[] simID) String[] simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/sim/abort/{simID}")
	public String abortSimulation(@pathVariable("simID") long simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/params")
	public String getMetaParameter() {
		String json = "jsonData";
		return json;
	}

	@PutMapping("/params")
	public String changeMetaParameters() {
		public String str = "ok";
		return str;
	}

	@GetMapping("/params/reset")
	public String resetMetaParameters() {
		public String str = "ok";
		return str;
	}

	@GetMapping("/sim/{simID}")
	public String getSimulation(@pathVariable("simID") long simID) {
		String json = "jsonData";
		return json;
	}

	@GetMapping("/plan")
	public String getPlans() {
		String json = "jsonData";
		return json;
	}
}



