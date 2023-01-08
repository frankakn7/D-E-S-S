package net.gruppe4.DiscreteEventSimulation;

import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimulationController {

	@Autowired
	private PlanRepository planRepository;
	//private final AtomicLong counter = new AtomicLong();

	@PostMapping("/plan/")
	public String upload(@RequestBody String json) {
		JSONObject obj = new JSONObject(json);
		Plan plan = new Plan(obj.getString("name"), obj.getJSONObject("plan").toString());
		planRepository.save(plan);
		return "created: "+plan.getUuid();
	}

	@GetMapping("/plan/all")
	public @ResponseBody Iterable<Plan> getAllPlans(){
		return planRepository.findAll();
	}

	@GetMapping("/plan/{planId}/start")
	public ResponseEntity<String> startSimulationUsingPlan(@PathVariable("planId") String planId) {
		/*Simulation sim = new Simulation(planID);
		return sim.getId(); */
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("content-type", "application/json");
		/*Plan plan = planRepository.findByUuid(planId);
		JSONObject obj = new JSONObject(plan.getPlanJson());
		return ResponseEntity.ok().headers(responseHeaders).body(obj.getJSONObject("plan").toString());*/

		JSONObject resultObj = new JSONObject();
		SimulationCase newCase = new SimulationCase(planRepository.findByUuid(planId));
		String results = newCase.runSimulation();
		resultObj.put("results",results);
		return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
	}

	@GetMapping("/sim/{simId}/status")
	public String simulationStatusCheck(@PathVariable("simId") long simID) {
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



