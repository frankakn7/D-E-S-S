package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Representing an operation taking place in a schedule.
 * Operations are executed by {@link Machine}s and belong to a {@link Job}.
 * They have a duration, a release date, and predecessors that must be
 * completed before they can be executed. They can also have a probability of
 * duration variation.
 */
public class Operation {
    private String id;
    private ArrayList<Operation> conditionalPredecessors;   // List of necessary predecessors in an AND relation
    private Operation machineQueuePredecessor = null;       // The Operation in the machine queue that this one follows
    // (null when first in queue)
    private Integer releaseDate;                            // Earliest possible date at which this operation can begin
    private Integer duration;
    private Machine machine;                                // Parent Machine
    private Job job;

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

    /**
     * Rolls the dice to determine if the operation duration should be varied
     * using a gaussian distribution.
     *
     * @return True if the duration should be varied, false otherwise.
     */
    public Boolean rollDiceForDurVariation() {
        this.duration = this.durationMean;
        if (Objects.equals(this.durVariationProb, 0.)) return false;

        if (this.durVariationProb >= this.generator.nextDouble()) {
            return true;
        }
        return false;
    }

    /**
     * Returns the full length of the operation, including any duration variation.
     * Uses gaussian distribution.
     *
     * @return The full length of the operation
     */
    public Integer rollDiceForVariedDurationLength() {
        Integer res = Math.max((int)Math.round((generator.nextGaussian(this.duration, this.durStandardDeviation))),1);
        return res;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
