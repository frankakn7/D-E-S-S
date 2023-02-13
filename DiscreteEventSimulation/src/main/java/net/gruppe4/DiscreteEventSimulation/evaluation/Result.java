package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result {
    private ArrayList<MachineStats> machines;
    private ArrayList<JobStats> jobs;
    private ArrayList<OperationStats> operations;
    private GeneralStats generalStats;

    public Result(ArrayList<MachineStats> machines, ArrayList<JobStats> jobs, ArrayList<OperationStats> operations, GeneralStats generalStats) {
        this.machines = machines;
        this.jobs = jobs;
        this.operations = operations;
        this.generalStats = generalStats;
    }

    public void addMachine(MachineStats machineStats){
        this.machines.add(machineStats);
    }

    public void addJob(JobStats jobStats){
        this.jobs.add(jobStats);
    }

    public void addOperation(OperationStats operationStats){
        this.operations.add(operationStats);
    }

    public ArrayList<MachineStats> getMachines() {
        return machines;
    }

    public ArrayList<JobStats> getJobs() {
        return jobs;
    }

    public ArrayList<OperationStats> getOperations() {
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

        for(MachineStats machineStats : this.machines){
            machinesJson.put(machineStats.toJsonObject());
        }

        for(JobStats jobStats : this.jobs){
            jobsJson.put(jobStats.toJsonObject());
        }

        for(OperationStats operationStats : this.operations){
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

