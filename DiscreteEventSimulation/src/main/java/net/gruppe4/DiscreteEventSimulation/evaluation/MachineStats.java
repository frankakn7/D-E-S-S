package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class MachineStats {
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
     * @return {
     * "id": "A",
     * "utilisation": {
     * "percent": {
     * "mean": 0.4,
     * "min": 0.3,
     * "max": 0.4,
     * "variance": 0.2
     * },
     * "time": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * }
     * },
     * "breakdowns": {
     * "downtime" : {
     * "mean": 0.4,
     * "min": 0.3,
     * "max": 0.4,
     * "variance": 0.2
     * },
     * "occurrence": {
     * "mean": 0.4,
     * "min": 0.3,
     * "max": 0.4,
     * "variance": 0.2
     * },
     * "percent":{
     * "mean": 0.4,
     * "min": 0.3,
     * "max": 0.4,
     * "variance": 0.2
     * }
     * },
     * "operational_cost": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * },
     * "repair_cost": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * }
     * }
     */
    public JSONObject toJsonObject() {
        JSONObject machineJson = new JSONObject();

        JSONObject utilisationJson = new JSONObject();
        JSONObject utilisationPercentJson = this.utilisationPercent.toJsonObject();
        JSONObject utilisationTimeJson = this.utilisationTime.toJsonObject();
        utilisationJson.put("percent", utilisationPercentJson);
        utilisationJson.put("time", utilisationTimeJson);

        JSONObject breakdownsJson = new JSONObject();
        JSONObject breakdownsDowntimeJson = this.breakdownsDowntime.toJsonObject();
        JSONObject breakdownsOccurrenceJson = this.breakdownsOccurrence.toJsonObject();
        JSONObject breakdownsPercentJson = this.breakdownsPercent.toJsonObject();
        breakdownsJson.put("downtime", breakdownsDowntimeJson);
        breakdownsJson.put("occurrence", breakdownsOccurrenceJson);
        breakdownsJson.put("percent", breakdownsPercentJson);

        JSONObject operationalCostJson = this.operationalCost.toJsonObject();
        JSONObject repairCostJson = this.repairCost.toJsonObject();

        machineJson.put("id", this.id);
        machineJson.put("utilisation", utilisationJson);
        machineJson.put("breakdowns", breakdownsJson);
        machineJson.put("operational_cost", operationalCostJson);
        machineJson.put("repair_cost", repairCostJson);

        return machineJson;
    }
}
