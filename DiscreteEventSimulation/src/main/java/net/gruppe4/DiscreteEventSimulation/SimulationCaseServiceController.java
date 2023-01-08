package net.gruppe4.DiscreteEventSimulation;

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

    @GetMapping("/sim/{simId}/results")
    public ResponseEntity<String> getResults(@PathVariable("simId") String simId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");

        return ResponseEntity.ok().headers(responseHeaders).body(simCaseService.getResults(simId));
    }

    @GetMapping("/sim/{simId}/status")
    public String simulationStatusCheck(@PathVariable("simId") String simId) {
        return "Ok";
    }
}
