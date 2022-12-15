package com.project.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
@RestController
public class SimulationController {

	private final AtomicLong counter = new AtomicLong();

	@PostMapping("/plan/upload")
	public boolean upload() {
		Plan plan = new Plan(counter.incrementAndGet());
		return true;
	}

	@PostMapping("/plan/start/{planID}")
	public long startSimulationUsingPlan(@PathVariable("planID") long planID) {
		Simulation sim = new Simulation(planID);
		return sim.getId(); 
	}

	@GetMapping("/sim/status/{simID}")
	public String simulationStatusCheck(@PathVariable("simID") long simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/server/status")
	public long serverStatusCheck() {
		return 0;
	}

	@GetMapping("/sim/results")
	public long[] getResults(@RequestParam(value = "limit", defaultValue = "1") int limit,@RequestParam(value = "id") String[] simID) {
		return new long[1];
	}

	@DeleteMapping("/sim")
	public String deleteSimulations(@RequestParam(value = "id") String[] simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/sim/abort/{simID}")
	public String abortSimulation(@PathVariable("simID") long simID) {
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
		String str = "ok";
		return str;
	}

	@GetMapping("/params/reset")
	public String resetMetaParameters() {
		String str = "ok";
		return str;
	}

	@GetMapping("/sim/{simID}")
	public String getSimulation(@PathVariable("simID") long simID) {
		String json = "jsonData";
		return json;
	}

	@GetMapping("/plan")
	public String getPlans() {
		String json = "jsonData";
		return json;
	}
}



