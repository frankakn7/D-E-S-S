package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class StatisticalValues {
    private double mean;
    private double min;
    private double max;
    private double variance;

    public StatisticalValues(double mean, double min, double max, double variance) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.variance = variance;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getVariance() {
        return variance;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public JSONObject toJsonObject() {
        JSONObject statJsonObj = new JSONObject();
        statJsonObj.put("mean", this.mean);
        statJsonObj.put("min", this.min);
        statJsonObj.put("max", this.max);
        statJsonObj.put("variance", this.variance);
        return statJsonObj;
    }
}
