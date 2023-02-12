package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class GeneralStats {
    private StatisticalValues totalCompletionTime;
    private StatisticalValues totalCost;
    private StatisticalValues totalRessourceUtilization;

    public GeneralStats(StatisticalValues totalCompletionTime, StatisticalValues totalCost, StatisticalValues totalRessourceUtilization) {
        this.totalCompletionTime = totalCompletionTime;
        this.totalCost = totalCost;
        this.totalRessourceUtilization = totalRessourceUtilization;
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
     * "total_ressource_utilization": {
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
        JSONObject totalRessourceUtilizationJson = this.totalRessourceUtilization.toJsonObject();

        generalStatsJson.put("total_completion_time", totalCompletionTimeJson);
        generalStatsJson.put("total_cost", totalCostJson);
        generalStatsJson.put("total_ressource_utilization", totalRessourceUtilizationJson);

        return generalStatsJson;
    }
}
