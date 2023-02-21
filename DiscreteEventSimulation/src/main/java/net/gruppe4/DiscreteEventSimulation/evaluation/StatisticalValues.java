package net.gruppe4.DiscreteEventSimulation.evaluation;

import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Represents the singular statistical values of each calculated metric.
 * Also used to calculate those metrics by adding values to them
 */
public class StatisticalValues {
    private double mean;
    private double min;
    private double max;
    private double variance;
    private double standardDeviation;
    private double count = 0;

    public StatisticalValues(){}

    public StatisticalValues(double mean, double min, double max, double variance, double standardDeviation) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.variance = variance;
        this.standardDeviation =  standardDeviation;
    }

    /**
     * Takes a value and calculates mean,min,max,variance and standard deviation over all Values
     * @param value - the new value to be added to all the metrics
     */
    public void addValue(double value) {
        if (this.count == 0){
            this.mean = value;
            this.min = value;
            this.max = value;
            this.variance = 0;
            this.standardDeviation = 0;
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
        double newStandardDeviation = Math.sqrt(newVariance);
        this.mean = newMean;
        this.variance = newVariance;
        this.standardDeviation = newStandardDeviation;
        /*System.out.println(newStandardDeviation / Math.sqrt(count));*/
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
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        JSONObject statJsonObj = new JSONObject();
        statJsonObj.put("mean", Double.parseDouble(decimalFormat.format(this.mean)));
        statJsonObj.put("min", Double.parseDouble(decimalFormat.format(this.min)));
        statJsonObj.put("max", Double.parseDouble(decimalFormat.format(this.max)));
        statJsonObj.put("variance", Double.parseDouble(decimalFormat.format(this.variance)));
        statJsonObj.put("standard_deviation", Double.parseDouble(decimalFormat.format(this.standardDeviation)));
        return statJsonObj;
    }
}
