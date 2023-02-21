package net.gruppe4.DiscreteEventSimulation.services;

import jakarta.transaction.Transactional;
import net.gruppe4.DiscreteEventSimulation.evaluation.*;
import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.objects.Status;
import net.gruppe4.DiscreteEventSimulation.repositories.SimulationCaseRepository;
import net.gruppe4.DiscreteEventSimulation.simulation.*;
/*import net.gruppe4.DiscreteEventSimulation.simulation.model.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Machine;*/
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
    @Transactional
    public long deleteSimCaseByUuid(String uuid){
        return simCaseRepo.deleteByUuid(uuid);
    }

    @Override
    @Transactional
    public long deleteSimCasesByPlanId(String planId){
        return simCaseRepo.deleteAllByPlanUuid(planId);
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
        HashMap<String, Job> jobs = extractJobs(planJsonObj.getJSONArray("jobs"));
        ArrayList<Operation> operations = extractOperations(planJsonObj.getJSONArray("operations"), machines, jobs);

        simStatus.setState("running");

        long startingTime = System.currentTimeMillis();

        //TODO Implement test Results
        //ArrayList<MachineStats> machineStats = new ArrayList<>();
        ArrayList<OperationStats> operationStats = new ArrayList<>();
        for(Operation op : operations) operationStats.add(new OperationStats(op));


        GeneralStats generalStats = new GeneralStats();
        ArrayList<MachineStats> machineStats = new ArrayList<>();
        for (Map.Entry<String, Machine> entry : machines.entrySet()) machineStats.add(new MachineStats(entry.getValue()));
        ArrayList<JobStats> jobStats = new ArrayList<>();
        for (Map.Entry<String, Job> entry : jobs.entrySet()) jobStats.add(new JobStats(entry.getValue()));
        //ArrayList<OperationStats> operationStats = new ArrayList<>();

        //TODO Here results are instantiated
        Result result = new Result(machineStats, jobStats, operationStats, generalStats);

        //TODO instantiate statistical values for all elements



        for (int i = 1; i < numOfSimulations; i++) {
            Simulation sim = new Simulation(machines, operations);
            EventLog log = sim.runSim();
            if (log == null) {
                simStatus.setState("logical_error");
                setResultsAndSave(simCaseUuid, "{}");
                break;
            }
            ArrayList<Job> jobList = new ArrayList<Job>(jobs.values());
            LogEvaluator evaluator = new LogEvaluator(log, machines, operations, jobList);

            //TODO example

            HashMap<Job, HashMap<String, Object>> jobValues = evaluator.calculateJobStatValues();
            for(JobStats jStat : result.getJobStats()) {
                jStat.lateness.addValue((double)jobValues.get(jStat.getJob()).get("lateness"));
                jStat.latenessCost.addValue((double)jobValues.get(jStat.getJob()).get("lateness_cost"));
                jStat.completionTime.addValue((double)jobValues.get(jStat.getJob()).get("completion_date"));
            }

            HashMap<Machine, HashMap<String, Object>> machineValues = evaluator.calculateMachineStatValues();
            for(MachineStats mStat : result.getMachineStats()) {
                mStat.utilisationPercent.addValue((double)machineValues.get(mStat.getMachine()).get("utilisation_percent"));
                mStat.utilisationTime.addValue((double)machineValues.get(mStat.getMachine()).get("utilisation_time"));
                mStat.repairCost.addValue((double)machineValues.get(mStat.getMachine()).get("repair_cost"));
                mStat.operationalCost.addValue((double)machineValues.get(mStat.getMachine()).get("operational_cost"));
                mStat.breakdownsTotalDowntime.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_downtime"));
                mStat.breakdownsOccurrence.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_occurrence"));
                mStat.breakdownsPercent.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_percent"));
                mStat.idleTime.addValue((double)machineValues.get(mStat.getMachine()).get("idle_time_absolute"));
            }

            HashMap<Operation, HashMap<String, Object>> operationValues = evaluator.calculateOperationStats(machineValues);
            for (OperationStats opStat : result.getOperationStats()) {
                opStat.length.addValue((double)(int) operationValues.get(opStat.getOperation()).get("length"));
            }


            HashMap<String, Object> generalValues = evaluator.calculateGeneralStats(machineValues, jobValues);
            result.getGeneralStats().totalResourceUtilization.addValue((double)generalValues.get("total_resource_utilisation"));
            result.getGeneralStats().totalCost.addValue((double)generalValues.get("total_cost"));
            result.getGeneralStats().totalCompletionTime.addValue((double)generalValues.get("total_completion_time"));


            //result = sim.runSim();
            simStatus.setProgress(i);
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            long estimatedTime = ((System.currentTimeMillis() - startingTime) / i) * (numOfSimulations - i);
            simStatus.setEstimatedMillisRemaining(estimatedTime);
            //TODO LogEvaluator call => Take logEvaluator data and pass it to results
            //System.out.println(result);
        }
        //TODO return results
        simStatus.setState("done");
        simStatus.setEstimatedMillisRemaining(0);
        System.out.println(result);

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

    private HashMap<String, Job> extractJobs(JSONArray jobsJson){
        HashMap<String, Job> jobs = new HashMap<>();
        for(int i = 0; i < jobsJson.length(); i++){
            JSONObject jobObj = jobsJson.getJSONObject(i);

            //TODO add release time
            Job job = new Job(
                    jobObj.getString("id"),
                    jobObj.getInt("release_date"),
                    jobObj.getInt("due_date"),
                    jobObj.getDouble("cost_per_lateness_time")
            );
            jobs.put(job.getId(),job);
        }
        return jobs;
    }

    private HashMap<String, Machine> extractMachines(JSONArray machinesJson) {
        HashMap<String, Machine> machines = new HashMap<>();
        for (int i = 0; i < machinesJson.length(); i++) {
            JSONObject machineObj = machinesJson.getJSONObject(i);
            //TODO add statistical values from json
            Machine machine = new Machine(
                    machineObj.getString("id"),
                    machineObj.getDouble("breakdown_probability"),
                    machineObj.getDouble("mean"),
                    machineObj.getDouble("standard_deviation"),
                    machineObj.getDouble("repair_cost_per_time"),
                    machineObj.getDouble("cost_per_time"));
            machines.put(machineObj.getString("id"), machine);
        }
        return machines;
    }

    //private Map<String, Operation> extractOperations(JSONArray operationsJson, Map<String, Job> jobs, Map<String, Machine> machines) {
    private ArrayList<Operation> extractOperations(JSONArray operationsJson, Map<String, Machine> machines, Map<String, Job> jobs) {
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
                    operationObj.getInt("release_date"),
                    operationObj.getInt("duration"),
                    machines.get(operationObj.getString("machine_id")),
                    jobs.get(operationObj.getString("job_id")),
                    operationObj.getDouble("duration_variation_probability"),
                    operationObj.getDouble("duration_standard_deviation")
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
