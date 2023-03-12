package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Represents the Results of the simulation as a Combination of all statistical values
 */
public class Result {
    private ArrayList<MachineStats> machineStats;
    private ArrayList<JobStats> jobStats;
    private ArrayList<OperationStats> operationStats;
    private GeneralStats generalStats;

    public Result(ArrayList<MachineStats> machines, ArrayList<JobStats> jobs, ArrayList<OperationStats> operations, GeneralStats generalStats) {
        this.machineStats = machines;
        this.jobStats = jobs;
        this.operationStats = operations;
        this.generalStats = generalStats;
    }

    public void setGeneralStats(GeneralStats generalStats) {
        this.generalStats = generalStats;
    }

    public GeneralStats getGeneralStats() {
        return generalStats;
    }

    /**
     * creates a {@link JSONObject} containing all the result information for all objects of the simulation case
     * @return
     */
    public JSONObject toJsonObject(){
        JSONObject resultsJson = new JSONObject();
        JSONArray machinesJson = new JSONArray();
        JSONArray jobsJson = new JSONArray();
        JSONArray operationsJson = new JSONArray();
        JSONObject generalStatsJson = new JSONObject();

        for(MachineStats machineStats : this.machineStats) {
            machinesJson.put(machineStats.toJsonObject());
        }

        for(JobStats jobStats : this.jobStats){
            jobsJson.put(jobStats.toJsonObject());
        }

        for(OperationStats operation : this.operationStats){
            operationsJson.put(operation.toJsonObject());
        }

        generalStatsJson = this.generalStats.toJsonObject();

        resultsJson.put("machines",machinesJson);
        resultsJson.put("jobs",jobsJson);
        resultsJson.put("operations",operationsJson);
        resultsJson.put("general_stats",generalStatsJson);

        return resultsJson;
    }

    public ArrayList<JobStats> getJobStats() {
        return jobStats;
    }

    public ArrayList<MachineStats> getMachineStats() {
        return machineStats;
    }

    public ArrayList<OperationStats> getOperationStats() {
        return operationStats;
    }

    @Override
    public String toString() {
        return this.toJsonObject().toString();
    }

}

