package net.gruppe4.DiscreteEventSimulation.controllers;

import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.services.SimulationCaseService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/sim/{simId}")
    public ResponseEntity<String> deleteSimCase(@PathVariable("simId") String simId){
        simCaseService.deleteSimCaseByUuid(simId);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok("successfully deleted "+simId);
    }

    @GetMapping("/sim/{simId}")
    public ResponseEntity<String> getSimCase(@PathVariable("simId") String simId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        JSONObject simCaseJson = simCaseService.getSimCaseJsonById(simId);
        return ResponseEntity.ok().headers(responseHeaders).body(simCaseJson.toString());
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
