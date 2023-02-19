package net.gruppe4.DiscreteEventSimulation.controllers;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.services.PlanService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlanServiceController {
    @Autowired
    PlanService planService;

    @PostMapping("/plan")
    public ResponseEntity<String> upload(@RequestBody String json) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject resultObj = new JSONObject();

        Plan plan = planService.createPlanFromJson(json);
        planService.savePlan(plan);
        resultObj.put("plan", plan.toJsonObject());
        return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
    }

    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<String> deletePlan(@PathVariable("planId") String planId){
        planService.deletePlanByUuid(planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/plan/all")
    public  ResponseEntity<String> getAllPlans(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject resultObj = new JSONObject();

        resultObj.put("plans",planService.getPlans());

        return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
    }

    @GetMapping("/plan/{planId}/simulate")
    //public ResponseEntity<String> startSimulationUsingPlan(@PathVariable("planId") String planId) {
    public ResponseEntity<String> startSimulationUsingPlan(@PathVariable("planId") String planId) {
		/*Simulation sim = new Simulation(planID);
		return sim.getId(); */
		/*Plan plan = planRepository.findByUuid(planId);
		JSONObject obj = new JSONObject(plan.getPlanJson());
		return ResponseEntity.ok().headers(responseHeaders).body(obj.getJSONObject("plan").toString());*/
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject resultObj = new JSONObject();

        String simCaseId = planService.createSimCase(planService.getPlanFromUuid(planId));
        planService.startSimCase(simCaseId);

        resultObj.put("sim_case_id",simCaseId);
        return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
    }
}
