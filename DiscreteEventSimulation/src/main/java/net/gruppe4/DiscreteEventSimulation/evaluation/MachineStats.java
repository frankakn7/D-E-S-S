package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.Machine;
import org.json.JSONObject;

public class MachineStats {
    Machine machine;
    public StatisticalValues utilisationPercent;
    public StatisticalValues utilisationTime;
    public StatisticalValues idleTime;
    public StatisticalValues operationalCost;
    public StatisticalValues repairCost;
    public StatisticalValues breakdownsDowntimePerBreakdown;
    public StatisticalValues breakdownsOccurrence;
    public StatisticalValues breakdownsPercent;
    public StatisticalValues breakdownsTotalDowntime;


    public MachineStats(Machine machine) {
        this.machine = machine;
        this.utilisationPercent = new StatisticalValues();
        this.utilisationTime = new StatisticalValues();
        this.idleTime = new StatisticalValues();
        this.operationalCost = new StatisticalValues();
        this.repairCost = new StatisticalValues();
        this.breakdownsDowntimePerBreakdown = new StatisticalValues();
        this.breakdownsOccurrence = new StatisticalValues();
        this.breakdownsPercent = new StatisticalValues();
        this.breakdownsTotalDowntime = new StatisticalValues();
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
        JSONObject idleTimeJson = this.idleTime.toJsonObject();
        utilisationJson.put("percent", utilisationPercentJson);
        utilisationJson.put("time", utilisationTimeJson);
        utilisationJson.put("idle_time", idleTimeJson);

        JSONObject breakdownsJson = new JSONObject();
        JSONObject breakdownsDowntimePerBreakdownJson = this.breakdownsDowntimePerBreakdown.toJsonObject();
        JSONObject breakdownsOccurrenceJson = this.breakdownsOccurrence.toJsonObject();
        JSONObject breakdownsPercentJson = this.breakdownsPercent.toJsonObject();
        JSONObject breakdownsTotalDowntimeJson = this.breakdownsTotalDowntime.toJsonObject();
        breakdownsJson.put("downtime_per_breakdown", breakdownsDowntimePerBreakdownJson);
        breakdownsJson.put("occurrence", breakdownsOccurrenceJson);
        breakdownsJson.put("percent", breakdownsPercentJson);
        breakdownsJson.put("total_downtime", breakdownsTotalDowntimeJson);

        JSONObject operationalCostJson = this.operationalCost.toJsonObject();
        JSONObject repairCostJson = this.repairCost.toJsonObject();

        machineJson.put("id", this.machine.getId());
        machineJson.put("utilisation", utilisationJson);
        machineJson.put("breakdowns", breakdownsJson);
        machineJson.put("operational_cost", operationalCostJson);
        machineJson.put("repair_cost", repairCostJson);

        return machineJson;
    }

    public Machine getMachine() {
        return machine;
    }
}
