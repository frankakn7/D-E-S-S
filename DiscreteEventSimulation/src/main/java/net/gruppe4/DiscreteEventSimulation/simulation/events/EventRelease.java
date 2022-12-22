package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public class EventRelease extends Event{
    private Operation operation;

    public EventRelease(Operation operation){
        super(0);
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }
}
