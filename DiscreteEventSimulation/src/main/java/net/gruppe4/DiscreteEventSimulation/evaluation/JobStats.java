package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

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
     * @return {
     * "id": "1",
     * "lateness": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * },
     * "lateness_cost": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * },
     * "completion_time": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * }
     * }
     */
    public JSONObject toJsonObject() {
        JSONObject jobJson = new JSONObject();

        JSONObject latenessJson = this.lateness.toJsonObject();
        JSONObject latenessCostJson = this.latenessCost.toJsonObject();
        JSONObject completionTimeJson = this.completionTime.toJsonObject();

        jobJson.put("id", this.id);
        jobJson.put("lateness", latenessJson);
        jobJson.put("lateness_cost", latenessCostJson);
        jobJson.put("completion_time", completionTimeJson);

        return jobJson;
    }
}
