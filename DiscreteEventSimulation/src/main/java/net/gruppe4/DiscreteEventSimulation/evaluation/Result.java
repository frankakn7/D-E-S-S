package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Result {
    private HashMap<String, MachineStats> machines;
    private HashMap<String, JobStats> jobs;
    private HashMap<String, OperationStats> operations;
    private GeneralStats generalStats;

    public Result(HashMap<String, MachineStats> machines, HashMap<String, JobStats> jobs, HashMap<String, OperationStats> operations, GeneralStats generalStats) {
        this.machines = machines;
        this.jobs = jobs;
        this.operations = operations;
        this.generalStats = generalStats;
    }

    public Result(){
        this.machines = new HashMap<>();
        this.jobs = new HashMap<>();
        this.operations = new HashMap<>();
        this.generalStats = new GeneralStats();
    }

    public void addMachine(String id, MachineStats machineStats){
        this.machines.put(id,machineStats);
    }

    public void addJob(String id, JobStats jobStats){
        this.jobs.put(id, jobStats);
    }

    public void addOperation(String id, OperationStats operationStats){
        this.operations.put(id, operationStats);
    }

    public HashMap<String, MachineStats> getMachines() {
        return machines;
    }

    public HashMap<String, JobStats> getJobs() {
        return jobs;
    }

    public HashMap<String, OperationStats> getOperations() {
        return operations;
    }

    public void setGeneralStats(GeneralStats generalStats) {
        this.generalStats = generalStats;
    }

    public GeneralStats getGeneralStats() {
        return generalStats;
    }

    public JSONObject toJsonObject(){
        JSONObject resultsJson = new JSONObject();
        JSONArray machinesJson = new JSONArray();
        JSONArray jobsJson = new JSONArray();
        JSONArray operationsJson = new JSONArray();
        JSONObject generalStatsJson = new JSONObject();

        for(Map.Entry<String, MachineStats> entry : this.machines.entrySet()){
            MachineStats machineStats = entry.getValue();
            machinesJson.put(machineStats.toJsonObject());
        }

        for(Map.Entry<String, JobStats> entry : this.jobs.entrySet()){
            JobStats jobStats = entry.getValue();
            jobsJson.put(jobStats.toJsonObject());
        }

        for(Map.Entry<String, OperationStats> entry : this.operations.entrySet()){
            OperationStats operationStats = entry.getValue();
            operationsJson.put(operationStats.toJsonObject());
        }

        generalStatsJson = this.generalStats.toJsonObject();

        resultsJson.put("machines",machinesJson);
        resultsJson.put("jobs",jobsJson);
        resultsJson.put("operations",operationsJson);
        resultsJson.put("general_stats",generalStatsJson);

        return resultsJson;
    }

    @Override
    public String toString() {
        return this.toJsonObject().toString();
    }
}

