package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * // TODO Comment OperationClass
 */
//TODO add jobId and implement JOB object for tracking and simulaton evaluation
public class Operation {
    private String id;
    // TODO Rename to something including the word dependency
    private ArrayList<Operation> conditionalPredecessors;   // List of necessary predecessors in an AND relation
    private Operation machineQueuePredecessor = null;       // The Operation in the machine queue that this one follows
    // (null when first in queue)
    private Integer releaseDate;                            // Earliest possible date at which this operation can begin
    private Integer duration;
    private Machine machine;                                // Parent Machine
    private Job job;

    // TODO Job in den Konstruktor hinzufügen
    private int durationMean;
    private Double durVariationProb = 0.;
    private Double durStandardDeviation;
    private Random generator;



    public Operation(String id, Operation machineQueuePredecessor,
                     ArrayList<Operation> conditionalPredecessors,
                     Integer releaseDate, Integer duration, Machine machine, Job job,
                     Double durVariationProb, Double durStandardDeviation) {
        this.id = id;
        this.conditionalPredecessors = conditionalPredecessors;
        this.machineQueuePredecessor = machineQueuePredecessor;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.durationMean = duration;
        this.machine = machine;
        this.job = job;
        this.durVariationProb = durVariationProb;
        this.durStandardDeviation = durStandardDeviation;
        this.generator = new Random();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    public Integer getReleaseDate() {
        return releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public Operation getMachineQueuePredecessor() {
        return machineQueuePredecessor;
    }

    public ArrayList<Operation> getConditionalPredecessors() {
        return conditionalPredecessors;
    }

    public void setConditionalPredecessors(ArrayList<Operation> conditionalPredecessors) {
        this.conditionalPredecessors = conditionalPredecessors;
    }

    public void setMachineQueuePredecessor(Operation machineQueuePredecessor) {
        this.machineQueuePredecessor = machineQueuePredecessor;
    }

    /**
     * Checks if {@link Operation} has another {@link Operation} object in the
     * same {@link Machine} scheduled in front of it.
     *
     * @return False if no other {@link Operation} is scheduled in front of it
     */
    public Boolean hasNoMachineQueuePredecessor() {
        if (this.machineQueuePredecessor == null) return true;
        return false;
    }

    public Machine getMachine() {
        return machine;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Boolean rollDiceForDurVariation() {
        this.duration = this.durationMean;
        if (Objects.equals(this.durVariationProb, 0.)) return false;

        if (this.durVariationProb >= this.generator.nextDouble()) {
            return true;
        }
        return false;
    }

    // Returns full length, not only variation
    // TODO rename, sounds too much like the method above
    public Integer rollDiceForVariationDur() {
        Integer res = Math.max((int)Math.round((generator.nextGaussian(this.durationMean, this.durStandardDeviation))),1);
        return res;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
