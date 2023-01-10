package net.gruppe4.DiscreteEventSimulation.controllers;

import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.services.SimulationCaseService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulationCaseServiceController {
    @Autowired
    SimulationCaseService simCaseService;

    @GetMapping("/sim/all")
    public ResponseEntity<String> getSimulationCases(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject resultObj = new JSONObject();
        JSONArray simCases = simCaseService.getSimCasesJson();
        resultObj.put("sim_cases",simCases);
        return ResponseEntity.ok().headers(responseHeaders).body(resultObj.toString());
    }

    @GetMapping("/sim/{simId}/results")
    public ResponseEntity<String> getResults(@PathVariable("simId") String simId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");

        return ResponseEntity.ok().headers(responseHeaders).body(simCaseService.getResults(simId));
    }

    @GetMapping("/sim/{simId}/status")
    public ResponseEntity<String> simulationStatusCheck(@PathVariable("simId") String simId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");

        try{
            return ResponseEntity.ok().headers(responseHeaders).body(simCaseService.getStatus(simId).toJsonString());
        }catch (Exception e){
            System.err.println(e);
            JSONObject resultObj = new JSONObject();

            resultObj.put("error","Sim with id "+simId+" does not exist");
            return ResponseEntity.badRequest().body(resultObj.toString());
        }
    }
}
