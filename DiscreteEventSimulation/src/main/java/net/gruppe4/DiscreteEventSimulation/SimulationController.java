package net.gruppe4.DiscreteEventSimulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimulationController {

	@Autowired
	private PlanRepository planRepository;
	@Autowired
	private SimulationCaseRepository simulationCaseRepository;
	//private final AtomicLong counter = new AtomicLong();



	@GetMapping("/server/status")
	public long serverStatusCheck() {
		return 0;
	}



	@DeleteMapping("/sim")
	public String deleteSimulations(@RequestParam(value = "id") String[] simID) {
		String str = "ok";
		return str;
	}

	@GetMapping("/sim/{simId}/abort ")
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



