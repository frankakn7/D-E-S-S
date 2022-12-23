package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public class EventFinished extends Event{
    public EventFinished(Operation operation) {
        super(operation);
    }
}
