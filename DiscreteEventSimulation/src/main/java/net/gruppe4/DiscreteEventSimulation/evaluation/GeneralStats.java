package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class GeneralStats {
    public StatisticalValues totalCompletionTime;
    public StatisticalValues totalCost;
    public StatisticalValues totalResourceUtilization;

    public GeneralStats(StatisticalValues totalCompletionTime, StatisticalValues totalCost, StatisticalValues totalresourceUtilization) {
        this.totalCompletionTime = totalCompletionTime;
        this.totalCost = totalCost;
        this.totalResourceUtilization = totalresourceUtilization;
    }

    public GeneralStats() {
        this.totalCompletionTime = new StatisticalValues();
        this.totalCost = new StatisticalValues();
        this.totalResourceUtilization = new StatisticalValues();
    }

    /**
     * @return "general_stats": {
     * "total_completion_time": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * },
     * "total_cost": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * },
     * "total_resource_utilization": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * }
     * }
     */
    public JSONObject toJsonObject() {
        JSONObject generalStatsJson = new JSONObject();

        JSONObject totalCompletionTimeJson = this.totalCompletionTime.toJsonObject();
        JSONObject totalCostJson = this.totalCost.toJsonObject();
        JSONObject totalResourceUtilizationJson = this.totalResourceUtilization.toJsonObject();

        generalStatsJson.put("total_completion_time", totalCompletionTimeJson);
        generalStatsJson.put("total_cost", totalCostJson);
        generalStatsJson.put("total_resource_utilization", totalResourceUtilizationJson);

        return generalStatsJson;
    }
}
