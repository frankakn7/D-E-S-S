package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.Machine;
import net.gruppe4.DiscreteEventSimulation.simulation.Operation;
import org.json.JSONObject;

/**
 * Represents the Operation statistical values of the simulation results
 */
public class OperationStats {
    private Operation operation;
    public StatisticalValues length;


    public OperationStats(Operation operation) {
        this.operation = operation;
        this.length = new StatisticalValues();
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

        OperationJson.put("id", this.operation.getId());
        OperationJson.put("machine_id", this.operation.getMachine().getId());
        OperationJson.put("job_id", this.operation.getJob().getId());
        OperationJson.put("length", lengthJson);

        return OperationJson;
    }

    public Operation getOperation() {
        return operation;
    }
}
