package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.evaluation.*;
import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.objects.Status;
import net.gruppe4.DiscreteEventSimulation.repositories.SimulationCaseRepository;
import net.gruppe4.DiscreteEventSimulation.simulation.Machine;
import net.gruppe4.DiscreteEventSimulation.simulation.Simulation;
/*import net.gruppe4.DiscreteEventSimulation.simulation.model.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Machine;*/
import net.gruppe4.DiscreteEventSimulation.simulation.Operation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class SimulationCaseServiceImpl implements SimulationCaseService {
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
        for (SimulationCase simCase : simCases) {
            simCaseArrJson.put(generateSimCaseJsonObject(simCase));
        }
        return simCaseArrJson;
    }

    @Override
    public JSONObject getSimCaseJsonById(String uuid) {
        SimulationCase simCase = simCaseRepo.findByUuid(uuid);
        return generateSimCaseJsonObject(simCase);
    }

    private JSONObject generateSimCaseJsonObject(SimulationCase simCase){
        JSONObject simCaseJson = new JSONObject();
        simCaseJson.put("id", simCase.getUuid());
        simCaseJson.put("results", new JSONObject(simCase.getResultJson()).getString("results"));
        simCaseJson.put("planId", simCase.getPlan().getUuid());
        simCaseJson.put("createdOn", simCase.getCreatedOn());
        return simCaseJson;
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
    public void setResultsAndSave(String uuid, String results) {
        SimulationCase simCase = simCaseRepo.findByUuid(uuid);
        JSONObject resultObj = new JSONObject();
        resultObj.put("results", results);
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
    public void runSimulation(String simCaseUuid) {
        SimulationCase simCase = simCaseRepo.findByUuid(simCaseUuid);
        Status simStatus = runningSimCaseStatus.get(simCaseUuid);

        int numOfSimulations = simStatus.getTotal();

        //TODO Get Machines in Hashmap from JSON
        JSONObject planJsonObj = new JSONObject(simCase.getPlan().getPlanJson());
        HashMap<String, Machine> machines = extractMachines(planJsonObj.getJSONArray("machines"));
        ArrayList<Operation> operations = extractOperations(planJsonObj.getJSONArray("operations"), machines);

        simStatus.setState("running");

        long startingTime = System.currentTimeMillis();

        //TODO Implement test Results
        ArrayList<MachineStats> machineStats = new ArrayList<>();
        ArrayList<JobStats> jobStats = new ArrayList<>();
        ArrayList<OperationStats> operationStats = new ArrayList<>();

        StatisticalValues exampleFullValues = new StatisticalValues(5.,2.,8.,3.);
        StatisticalValues exampleFullValuesDifferent = new StatisticalValues(7,3,10,4);
        StatisticalValues examplePercentValues = new StatisticalValues(0.3,0.1,0.7,0.4);

        machineStats.add(new MachineStats("A",examplePercentValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,examplePercentValues, exampleFullValues));
        machineStats.add(new MachineStats("B",examplePercentValues,exampleFullValues,exampleFullValuesDifferent,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,examplePercentValues, exampleFullValues));
        machineStats.add(new MachineStats("C",examplePercentValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,exampleFullValues,examplePercentValues, exampleFullValues));

        jobStats.add(new JobStats("1",exampleFullValues,exampleFullValues,exampleFullValues));
        jobStats.add(new JobStats("2",exampleFullValues,exampleFullValues,exampleFullValues));
        jobStats.add(new JobStats("3",exampleFullValues,exampleFullValues,exampleFullValues));

        operationStats.add(new OperationStats("op1","A","1",exampleFullValues));
        operationStats.add(new OperationStats("op2","A","2",exampleFullValues));
        operationStats.add(new OperationStats("op3","A","3",exampleFullValues));
        operationStats.add(new OperationStats("op4","B","1",exampleFullValues));
        operationStats.add(new OperationStats("op5","B","2",exampleFullValues));
        operationStats.add(new OperationStats("op6","B","3",exampleFullValues));
        operationStats.add(new OperationStats("op7","C","1",exampleFullValues));
        operationStats.add(new OperationStats("op8","C","2",exampleFullValues));
        operationStats.add(new OperationStats("op9","C","3",exampleFullValues));

        GeneralStats generalStats = new GeneralStats(exampleFullValues,exampleFullValues,examplePercentValues);


        Result result = new Result(machineStats,jobStats,operationStats,generalStats);
        for (int i = 1; i < numOfSimulations; i++) {
            Simulation sim = new Simulation(machines, operations);
            //result = sim.runSim();
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

    //TODO implement Jobs for tracking of job status etc
    /*private Map<String, Job> extractJobs(JSONArray jobsJson) {
        Map<String, Job> jobs = new HashMap<>();
        for (int i = 0; i < jobsJson.length(); i++) {
            JSONObject jobObj = jobsJson.getJSONObject(i);
            Job job = new Job(jobObj.getString("id"), jobObj.getInt("releaseTime"));
            jobs.put(jobObj.getString("id"), job);
        }
        return jobs;
    }*/

    private HashMap<String, Machine> extractMachines(JSONArray machinesJson) {
        HashMap<String, Machine> machines = new HashMap<>();
        for (int i = 0; i < machinesJson.length(); i++) {
            JSONObject machineObj = machinesJson.getJSONObject(i);
            Machine machine = new Machine(machineObj.getString("id"), machineObj.getDouble("breakdown_probability"), machineObj.getDouble("mean"), machineObj.getDouble("standard_deviation"));
            machines.put(machineObj.getString("id"), machine);
        }
        return machines;
    }

    //private Map<String, Operation> extractOperations(JSONArray operationsJson, Map<String, Job> jobs, Map<String, Machine> machines) {
    private ArrayList<Operation> extractOperations(JSONArray operationsJson, Map<String, Machine> machines) {
        //<Operation ID, Operation>
        Map<String, Operation> operations = new HashMap<>();
        //<Operation ID, Machine Predecessor Operation ID>
        Map<String, String> AllMachinePredIds = new HashMap<>();
        //<Operation ID, [Conditional Operations Ids]>
        Map<String, JSONArray> AllConditionalPredIds = new HashMap<>();

        for (int i = 0; i < operationsJson.length(); i++) {
            JSONObject operationObj = operationsJson.getJSONObject(i);
            //TODO Fix this deserialization of operation objects
            Operation operation = new Operation(
                    operationObj.getString("id"),
                    null,
                    null,
                    //operationObj.getInt("release_date"),
                    0,
                    //jobs.get(operationObj.getString("job_id")),
                    operationObj.getInt("duration"),
                    machines.get(operationObj.getString("machine_id"))
            );
            //Check if is null (error if not checked)
            String machinePredId = operationObj.isNull("machine_pred") ? null : operationObj.getString("machine_pred");
            AllMachinePredIds.put(operationObj.getString("id"), machinePredId);

            //Check if is null (error if not checked)
            JSONArray conditionalPredIds = operationObj.isNull("conditional_preds") ? new JSONArray() : operationObj.getJSONArray("conditional_preds");
            AllConditionalPredIds.put(operationObj.getString("id"), conditionalPredIds);
            operations.put(operationObj.getString("id"), operation);
        }

        for (Operation op : operations.values()) {
            Operation machinePred = operations.get(AllMachinePredIds.get(op.getId()));
            ArrayList<Operation> conditionalPredsOperations = getConditionalPreds(op.getId(), AllConditionalPredIds, operations);
            op.setMachineQueuePredecessor(machinePred);
            op.setConditionalPredecessors(conditionalPredsOperations);
        }

        return new ArrayList<Operation>(operations.values());
    }

    private ArrayList<Operation> getConditionalPreds(String operationId, Map<String, JSONArray> conditionalPreds, Map<String, Operation> operations) {
        JSONArray operationIds = conditionalPreds.get(operationId);
        ArrayList<Operation> conditionalPredsOperations = new ArrayList<>();

        for (int i = 0; i < operationIds.length(); i++) {
            String opId = operationIds.getString(i);
            conditionalPredsOperations.add(operations.get(opId));
        }
        return conditionalPredsOperations;
    }
}
