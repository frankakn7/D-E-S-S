package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.objects.Status;
import net.gruppe4.DiscreteEventSimulation.repositories.SimulationCaseRepository;
import net.gruppe4.DiscreteEventSimulation.simulation.EventLog;
import net.gruppe4.DiscreteEventSimulation.simulation.Simulation;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Machine;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SimulationCaseServiceImpl implements SimulationCaseService{
    @Autowired
    SimulationCaseRepository simCaseRepo;

    HashMap<String, Status> runningSimCaseStatus = new HashMap<>();

    @Override
    public SimulationCase createSimCase(Plan plan) {
        return new SimulationCase(plan);
    }

    @Override
    public SimulationCase getSimCaseById(String uuid) {
        return simCaseRepo.findByUuid(uuid);
    }

    @Override
    public JSONArray getSimCasesJson() {
        Iterable<SimulationCase> simCases = simCaseRepo.findAll();
        JSONArray simCaseArrJson = new JSONArray();
        for(SimulationCase simCase : simCases){
            JSONObject simCaseJson = new JSONObject();
            simCaseJson.put("id",simCase.getUuid());
            simCaseJson.put("results",new JSONObject(simCase.getResultJson()).getString("results"));
            simCaseJson.put("plan_id",simCase.getPlan().getUuid());
            simCaseArrJson.put(simCaseJson);
        }
        return simCaseArrJson;
    }

    @Override
    public void saveSimCase(SimulationCase simCase) {
        simCaseRepo.save(simCase);
    }

    @Override
    public String getResults(String uuid) {
        SimulationCase simCase = simCaseRepo.findByUuid(uuid);
        return simCase.getResultJson();
    }

    @Override
    public void setResultsAndSave(String uuid, String results){
        SimulationCase simCase = simCaseRepo.findByUuid(uuid);
        JSONObject resultObj = new JSONObject();
        resultObj.put("results",results);
        simCase.setResultJson(resultObj.toString());
        simCaseRepo.save(simCase);
    }

    @Override
    public Status getStatus(String uuid) {
        return runningSimCaseStatus.get(uuid);
    }

    @Override
    public void setAndSaveStatus(String simCaseUuid, int numOfSimulations) {
        Status simStatus = new Status("creating", numOfSimulations);
        runningSimCaseStatus.put(simCaseUuid, simStatus);
    }

    @Override
    public void runSimulation(String simCaseUuid){
        SimulationCase simCase = simCaseRepo.findByUuid(simCaseUuid);
        Status simStatus = runningSimCaseStatus.get(simCaseUuid);
        int numOfSimulations = simStatus.getTotal();
        ArrayList<Operation> operations = initOperations(new JSONObject(simCase.getPlan().getPlanJson()));
        simStatus.setState("running");
        long startingTime = System.currentTimeMillis();
        EventLog result = null;
        for(int i = 1; i < numOfSimulations; i++){
            Simulation sim = new Simulation(operations);
            result = sim.simulationLoop();
            simStatus.setProgress(i);
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            long estimatedTime = ((System.currentTimeMillis() - startingTime) / i) * (numOfSimulations - i);
            simStatus.setEstimatedMillisRemaining(estimatedTime);
            //System.out.println(result);
        }
        simStatus.setState("done");
        simStatus.setEstimatedMillisRemaining(0);

        setResultsAndSave(simCaseUuid, result.toString());
    }

    private ArrayList<Operation> initOperations(JSONObject planJsonObj) {
        Map<String, Job> jobs = extractJobs(planJsonObj.getJSONArray("jobs"));
        Map<String, Machine> machines = extractMachines(planJsonObj.getJSONArray("machines"));
        Map<String, Operation> operations = extractOperations(planJsonObj.getJSONArray("operations"), jobs, machines);
        /*System.out.println(jobs);
        System.out.println(machines);
        System.out.println(operations);*/
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
        return operations;
    }
}