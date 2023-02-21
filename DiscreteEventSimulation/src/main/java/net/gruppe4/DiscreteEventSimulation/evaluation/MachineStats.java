package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.Machine;
import org.json.JSONObject;

/**
 * Represents the Machine statistical values of the simulation results
 */
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
