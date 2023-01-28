package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.ArrayList;

/**
 * // TODO Comment OperationClass
 */
public class Operation {
    private String id;
    // TODO Rename to something including the word dependency
    private ArrayList<Operation> conditionalPredecessors;   // List of necessary predecessors in an AND relation
    private Operation machineQueuePredecessor = null;       // The Operation in the machine queue that this one follows
                                                            // (null when first in queue)
    private Integer releaseDate;                            // Earliest possible date at which this operation can begin
    private Integer duration;
    private Machine machine;                                // Parent Machine

    public Operation(String id, Operation machineQueuePredecessor,
                     ArrayList<Operation> conditionalPredecessors,
                     Integer releaseDate, Integer duration, Machine machine) {
        this.id = id;
        this.conditionalPredecessors = conditionalPredecessors;
        this.machineQueuePredecessor = machineQueuePredecessor;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.machine = machine;
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

    /**
     * Checks if {@link Operation} has another {@link Operation} object in the
     * same {@link Machine} scheduled in front of it.
     *
     * @return  False if no other {@link Operation} is scheduled in front of it
     */
    public Boolean hasNoMachineQueuePredecessor() {
        if (this.machineQueuePredecessor == null) return true;
        return false;
    }

    public Machine getMachine() {
        return machine;
    }
}
