package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.Job;
import org.json.JSONObject;

public class JobStats {

    private Job job;
    public StatisticalValues lateness;
    public StatisticalValues latenessCost;
    public StatisticalValues completionTime;


    public JobStats(Job job){
        this.job = job;
        this.lateness = new StatisticalValues();
        this.latenessCost = new StatisticalValues();
        this.completionTime = new StatisticalValues();
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

        jobJson.put("id", this.job.getId());
        jobJson.put("lateness", latenessJson);
        jobJson.put("lateness_cost", latenessCostJson);
        jobJson.put("completion_time", completionTimeJson);

        return jobJson;
    }


    public Job getJob() {
        return job;
    }
}
