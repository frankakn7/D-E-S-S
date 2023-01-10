package net.gruppe4.DiscreteEventSimulation.controllers;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlanServiceController {
    @Autowired
    PlanService planService;

    @PostMapping("/plan/")
    public String upload(@RequestBody String json) {
        Plan plan = planService.createPlanFromJson(json);
        planService.savePlan(plan);
        return "created: "+plan.getUuid();
    }

    @GetMapping("/plan/all")
    public @ResponseBody Iterable<Plan> getAllPlans(){
        return planService.getPlans();
    }

    @GetMapping("/plan/{planId}/simulate")
    //public ResponseEntity<String> startSimulationUsingPlan(@PathVariable("planId") String planId) {
    public String startSimulationUsingPlan(@PathVariable("planId") String planId) {
		/*Simulation sim = new Simulation(planID);
		return sim.getId(); */
		/*Plan plan = planRepository.findByUuid(planId);
		JSONObject obj = new JSONObject(plan.getPlanJson());
		return ResponseEntity.ok().headers(responseHeaders).body(obj.getJSONObject("plan").toString());*/

        //HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.set("content-type", "application/json");
        //JSONObject resultObj = new JSONObject();
        String simCaseId = planService.createSimCase(planService.getPlanFromUuid(planId));
        planService.startSimCase(simCaseId);
        //String results = newCase.runSimulation();
        //resultObj.put("results",results);
        //return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
        //return "started Simulation: "+simCaseId;
        return simCaseId;
    }
}
