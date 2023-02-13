package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

public class StatisticalValues {
    private double mean;
    private double min;
    private double max;
    private double variance;
    private double count = 0;

    public StatisticalValues(){}

    public StatisticalValues(double mean, double min, double max, double variance) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.variance = variance;
    }

    //TODO add function will be here
    public void addValue(double value) {
        if (this.count == 0){
            this.mean = value;
            this.min = value;
            this.max = value;
            this.variance = 0;
            this.count = 1;
            return;
        }
        if(value > this.max) {
            this.max = value;
        }else if(value < this.min){
            this.min = value;
        }

        double newMean = (this.mean * this.count + value) / (this.count + 1);
        double newVariance = (this.count / (this.count + 1)) * (this.variance + Math.pow((this.mean - value),2) / (this.count + 1));
        this.mean = newMean;
        this.variance = newVariance;
        this.count += 1;
    }

    public double getMean() {
        return this.mean;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getVariance() {
        return this.variance;
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
