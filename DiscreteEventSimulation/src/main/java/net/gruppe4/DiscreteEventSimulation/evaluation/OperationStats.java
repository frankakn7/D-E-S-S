package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class OperationStats {
    private String id;
    private String machineId;
    private String jobId;
    private StatisticalValues length;

    public OperationStats(String id, String machineId, String jobId, StatisticalValues length) {
        this.id = id;
        this.machineId = machineId;
        this.jobId = jobId;
        this.length = length;
    }

    /**
     * @return {
     * "id": "op1",
     * "length": {
     * "mean": 4,
     * "min": 3,
     * "max": 4,
     * "variance": 5
     * }
     * }
     */
    public JSONObject toJsonObject() {
        JSONObject OperationJson = new JSONObject();

        JSONObject lengthJson = this.length.toJsonObject();

        OperationJson.put("id", this.id);
        OperationJson.put("length", lengthJson);

        return OperationJson;
    }
}
