package net.gruppe4.DiscreteEventSimulation.objects;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Represents the status of a currently running simulation.
 * Displays the total number of simulation runs, the progress in number of runs completed, the estimatedMillisRemaining and the state
 */
public class Status {
    private String state;
    private int total;  //Simulation Runs that need to be done
    private int progress;   //total sim runs that have been done
    private long estimatedMillisRemaining;

    public Status(String state, int total) {
        this.state = state;
        this.total = total;
        this.progress = 0;
        this.estimatedMillisRemaining = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1);
    }

    @Override
    public String toString() {
        return "Status{" +
                "state='" + state + '\'' +
                ", total=" + total +
                ", progress=" + progress +
                ", estimatedMillisRemaining=" + estimatedMillisRemaining +
                '}';
    }

    public String toJsonString(){
        JSONObject obj = new JSONObject();
        obj.put("state", this.state);
        obj.put("total", this.total);
        obj.put("progress",this.progress);
        obj.put("estimatedMillisRemaining", this.estimatedMillisRemaining);
        return obj.toString();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotal() {
        return total;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getEstimatedMillisRemaining() {
        return estimatedMillisRemaining;
    }

    public void setEstimatedMillisRemaining(long estimatedMillisRemaining) {
        this.estimatedMillisRemaining = estimatedMillisRemaining;
    }
}
