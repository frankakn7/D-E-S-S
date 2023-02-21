package net.gruppe4.DiscreteEventSimulation.controllers;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.services.PlanService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Defines all endpoints that are connected to plan objects
 */
@RestController
public class PlanServiceController {
    @Autowired
    PlanService planService;

    /**
     * Upload a new plan
     * @param json - takes the request body including name and json file
     * @return a response containing the plan object as json
     */
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

    /**
     * Start a simulation of the plan => automatically creates a simCase object
     * @param planId - plan id to be simulated
     * @param numOfSimulations - number of simulation runs to do
     * @return the id of the newly created simulation case
     */
    @GetMapping("/plan/{planId}/simulate/{numOfSimulations}")
    public ResponseEntity<String> startSimulationUsingPlan(@PathVariable("planId") String planId, @PathVariable("numOfSimulations") Integer numOfSimulations) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject resultObj = new JSONObject();

        String simCaseId = planService.createSimCase(planService.getPlanFromUuid(planId), numOfSimulations);
        planService.startSimCase(simCaseId);

        resultObj.put("sim_case_id",simCaseId);
        return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
    }
}
