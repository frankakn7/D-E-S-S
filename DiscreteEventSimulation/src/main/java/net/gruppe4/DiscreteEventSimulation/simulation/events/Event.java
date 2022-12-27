package net.gruppe4.DiscreteEventSimulation.simulation.events;

import org.javatuples.Pair;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public abstract class Event {
    protected String id;
    protected Operation operation;
    protected String name;
    protected Integer time;

    public Event(Operation operation, Integer time) {
        this.operation = operation;
        this.time = time;
    }

    public void executeSimulationStateUpdates() {
    }

    public boolean isDoable() {
        return true;
    }

    /**
     * Overridable function that returns the event that must follow after this one
     * or null if there is none.
     * @return the event that must follow after this one or null
     */
    public Event getFollowingEvent() {
        return null;
    }

    @Override
    public String toString() {
        return this.name + this.operation.getJobMachineCombinationId();
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
