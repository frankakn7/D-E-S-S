package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class GeneralStats {
    private StatisticalValues completionTime;
    private StatisticalValues totalCost;
    private StatisticalValues totalRessourceUtilization;

    public GeneralStats(StatisticalValues completionTime, StatisticalValues totalCost, StatisticalValues totalRessourceUtilization) {
        this.completionTime = completionTime;
        this.totalCost = totalCost;
        this.totalRessourceUtilization = totalRessourceUtilization;
    }

    /**
     * @return "general_stats": {
     * "completion_time": {
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

        JSONObject completionTimeJson = this.completionTime.toJsonObject();
        JSONObject totalCostJson = this.totalCost.toJsonObject();
        JSONObject totalRessourceUtilizationJson = this.totalRessourceUtilization.toJsonObject();

        generalStatsJson.put("completion_time", completionTimeJson);
        generalStatsJson.put("total_cost", totalCostJson);
        generalStatsJson.put("total_ressource_utilization", totalRessourceUtilizationJson);

        return generalStatsJson;
    }
}
