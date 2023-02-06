package net.gruppe4.DiscreteEventSimulation;

import org.apache.tomcat.util.collections.ManagedConcurrentWeakHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Results {
    private ArrayList<MachineStats> machines;
    private ArrayList<JobStats> jobs;
    private ArrayList<OperationStats> operations;
    private GeneralStats generalStats;

    public Results(ArrayList<MachineStats> machines, ArrayList<JobStats> jobs, ArrayList<OperationStats> operations, GeneralStats generalStats) {
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
}

class MachineStats {
    private String id;
    private StatisticalValues utilisationPercent;
    private StatisticalValues utilisationTime;
    private StatisticalValues operationalCost;
    private StatisticalValues repairCost;
    private StatisticalValues breakdownsDowntime;
    private StatisticalValues breakdownsOccurrence;
    private StatisticalValues breakdownsPercent;

    public MachineStats(String id, StatisticalValues utilisationPercent, StatisticalValues utilisationTime, StatisticalValues operationalCost, StatisticalValues repairCost, StatisticalValues breakdownsDowntime, StatisticalValues breakdownsOccurrence, StatisticalValues breakdownsPercent) {
        this.id = id;
        this.utilisationPercent = utilisationPercent;
        this.utilisationTime = utilisationTime;
        this.operationalCost = operationalCost;
        this.repairCost = repairCost;
        this.breakdownsDowntime = breakdownsDowntime;
        this.breakdownsOccurrence = breakdownsOccurrence;
        this.breakdownsPercent = breakdownsPercent;
    }

    /**
     *
     * @return
     * {
     *             "id": "A",
     *             "utilisation": {
     *                 "percent": {
     *                     "mean": 0.4,
     *                     "min": 0.3,
     *                     "max": 0.4,
     *                     "variance": 0.2
     *                 },
     *                 "time": {
     *                     "mean": 4,
     *                     "min": 3,
     *                     "max": 4,
     *                     "variance": 5
     *                 }
     *             },
     *             "breakdowns": {
     *                 "downtime" : {
     *                     "mean": 0.4,
     *                     "min": 0.3,
     *                     "max": 0.4,
     *                     "variance": 0.2
     *                 },
     *                 "occurrence": {
     *                     "mean": 0.4,
     *                     "min": 0.3,
     *                     "max": 0.4,
     *                     "variance": 0.2
     *                 },
     *                 "percent":{
     *                     "mean": 0.4,
     *                     "min": 0.3,
     *                     "max": 0.4,
     *                     "variance": 0.2
     *                 }
     *             },
     *             "operational_cost": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             },
     *             "repair_cost": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             }
     *         }
     */
    public JSONObject toJsonObject(){
        JSONObject machineJson = new JSONObject();

        JSONObject utilisationJson = new JSONObject();
        JSONObject utilisationPercentJson = this.utilisationPercent.toJsonObject();
        JSONObject utilisationTimeJson = this.utilisationTime.toJsonObject();
        utilisationJson.put("percent",utilisationPercentJson);
        utilisationJson.put("time",utilisationTimeJson);

        JSONObject breakdownsJson = new JSONObject();
        JSONObject breakdownsDowntimeJson = this.breakdownsDowntime.toJsonObject();
        JSONObject breakdownsOccurrenceJson = this.breakdownsOccurrence.toJsonObject();
        JSONObject breakdownsPercentJson = this.breakdownsPercent.toJsonObject();
        breakdownsJson.put("downtime",breakdownsDowntimeJson);
        breakdownsJson.put("occurrence", breakdownsOccurrenceJson);
        breakdownsJson.put("percent", breakdownsPercentJson);

        JSONObject operationalCostJson = this.operationalCost.toJsonObject();
        JSONObject repairCostJson = this.repairCost.toJsonObject();

        machineJson.put("id",this.id);
        machineJson.put("utilisation", utilisationJson);
        machineJson.put("breakdowns", breakdownsJson);
        machineJson.put("operational_cost", operationalCostJson);
        machineJson.put("repair_cost", repairCostJson);

        return machineJson;
    }
}

class JobStats {
    private String id;
    private StatisticalValues lateness;
    private StatisticalValues latenessCost;
    private StatisticalValues completionTime;

    public JobStats(String id, StatisticalValues lateness, StatisticalValues latenessCost, StatisticalValues completionTime) {
        this.id = id;
        this.lateness = lateness;
        this.latenessCost = latenessCost;
        this.completionTime = completionTime;
    }

    /**
     *
     * @return
     * {
     *             "id": "1",
     *             "lateness": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             },
     *             "lateness_cost": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             },
     *             "completion_time": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             }
     *         }
     */
    public JSONObject toJsonObject(){
        JSONObject jobJson = new JSONObject();

        JSONObject latenessJson = this.lateness.toJsonObject();
        JSONObject latenessCostJson = this.latenessCost.toJsonObject();
        JSONObject completionTimeJson = this.completionTime.toJsonObject();

        jobJson.put("id",this.id);
        jobJson.put("lateness",latenessJson);
        jobJson.put("lateness_cost", latenessCostJson);
        jobJson.put("completion_time", completionTimeJson);

        return jobJson;
    }
}

class OperationStats {
    private String id;
    private StatisticalValues length;

    public OperationStats(String id, StatisticalValues length) {
        this.id = id;
        this.length = length;
    }

    /**
     *
     * @return
     * {
     *             "id": "op1",
     *             "length": {
     *                 "mean": 4,
     *                 "min": 3,
     *                 "max": 4,
     *                 "variance": 5
     *             }
     *         }
     */
    public JSONObject toJsonObject(){
        JSONObject OperationJson = new JSONObject();

        JSONObject lengthJson = this.length.toJsonObject();

        OperationJson.put("id",this.id);
        OperationJson.put("length",lengthJson);

        return OperationJson;
    }
}

class GeneralStats {
    private StatisticalValues completionTime;
    private StatisticalValues totalCost;
    private StatisticalValues totalRessourceUtilization;

    public GeneralStats(StatisticalValues completionTime, StatisticalValues totalCost, StatisticalValues totalRessourceUtilization) {
        this.completionTime = completionTime;
        this.totalCost = totalCost;
        this.totalRessourceUtilization = totalRessourceUtilization;
    }

    /**
     *
     * @return
     * "general_stats": {
     *         "completion_time": {
     *             "mean": 4,
     *             "min": 3,
     *             "max": 4,
     *             "variance": 5
     *         },
     *         "total_cost": {
     *             "mean": 4,
     *             "min": 3,
     *             "max": 4,
     *             "variance": 5
     *         },
     *         "total_ressource_utilization": {
     *             "mean": 4,
     *             "min": 3,
     *             "max": 4,
     *             "variance": 5
     *         }
     *     }
     */
    public JSONObject toJsonObject(){
        JSONObject generalStatsJson = new JSONObject();

        JSONObject completionTimeJson = this.completionTime.toJsonObject();
        JSONObject totalCostJson = this.totalCost.toJsonObject();
        JSONObject totalRessourceUtilizationJson = this.totalRessourceUtilization.toJsonObject();

        generalStatsJson.put("completion_time", completionTimeJson);
        generalStatsJson.put("completion_time", totalCostJson);
        generalStatsJson.put("completion_time", totalRessourceUtilizationJson);

        return generalStatsJson;
    }
}

class StatisticalValues {
    private double mean;
    private double min;
    private double max;
    private double variance;

    public StatisticalValues(double mean, double min, double max, double variance) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.variance = variance;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getVariance() {
        return variance;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public JSONObject toJsonObject(){
        JSONObject statJsonObj = new JSONObject();
        statJsonObj.put("mean",this.mean);
        statJsonObj.put("min",this.min);
        statJsonObj.put("max",this.max);
        statJsonObj.put("variance",this.variance);
        return statJsonObj;
    }
}

