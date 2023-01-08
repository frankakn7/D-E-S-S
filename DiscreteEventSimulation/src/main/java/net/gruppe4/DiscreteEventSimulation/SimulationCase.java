package net.gruppe4.DiscreteEventSimulation;

import jakarta.persistence.*;
import net.gruppe4.DiscreteEventSimulation.simulation.EventLog;
import net.gruppe4.DiscreteEventSimulation.simulation.Simulation;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Machine;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class SimulationCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "planId")
    private Plan plan;


    protected SimulationCase() {
    }

    ;

    public SimulationCase(Plan plan) {
        this.plan = plan;

    }

    public String runSimulation(){
        ArrayList<Operation> operations = initOperations(new JSONObject(this.plan.getPlanJson()));
        System.out.println(operations);
        Simulation sim = new Simulation(operations);

        EventLog result = sim.simulationLoop();
        return result.toString();
    }

    private ArrayList<Operation> initOperations(JSONObject planJsonObj) {
        Map<String, Job> jobs = extractJobs(planJsonObj.getJSONArray("jobs"));
        Map<String, Machine> machines = extractMachines(planJsonObj.getJSONArray("machines"));
        Map<String, Operation> operations = extractOperations(planJsonObj.getJSONArray("operations"), jobs, machines);
        System.out.println(jobs);
        System.out.println(machines);
        System.out.println(operations);
        return new ArrayList<Operation>(operations.values());
    }

    private Map<String, Job> extractJobs(JSONArray jobsJson) {
        Map<String, Job> jobs = new HashMap<>();
        for (int i = 0; i < jobsJson.length(); i++) {
            JSONObject jobObj = jobsJson.getJSONObject(i);
            Job job = new Job(jobObj.getString("id"), jobObj.getInt("releaseTime"));
            jobs.put(jobObj.getString("id"), job);
        }
        return jobs;
    }

    private Map<String, Machine> extractMachines(JSONArray machinesJson) {
        Map<String, Machine> machines = new HashMap<>();
        for (int i = 0; i < machinesJson.length(); i++) {
            JSONObject machineObj = machinesJson.getJSONObject(i);
            Machine machine = new Machine(machineObj.getString("id"));
            machines.put(machineObj.getString("id"), machine);
        }
        return machines;
    }

    private Map<String, Operation> extractOperations(JSONArray operationsJson, Map<String, Job> jobs, Map<String, Machine> machines) {
        Map<String, Operation> operations = new HashMap<>();
        for (int i = 0; i < operationsJson.length(); i++) {
            JSONObject operationObj = operationsJson.getJSONObject(i);
            Operation operation = new Operation(
                    jobs.get(operationObj.getString("job_id")),
                    machines.get(operationObj.getString("machine_id")),
                    operationObj.getInt("duration"),
                    operationObj.getString("id"),
                    operations.get(operationObj.get("predecessor"))
            );
            operations.put(operationObj.getString("id"), operation);
        }
        /*for(int i = 0; i < operationsJson.length(); i++) {
            JSONObject operationObj = operationsJson.getJSONObject(i);
            Operation op = operations.get(operationObj.getString("id"));
        }*/
        return operations;
    }
}
