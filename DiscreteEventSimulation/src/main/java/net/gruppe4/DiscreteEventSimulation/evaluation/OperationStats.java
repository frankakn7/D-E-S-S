package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

class OperationStats {
    private String id;
    private StatisticalValues length;

    public OperationStats(String id, StatisticalValues length) {
        this.id = id;
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
