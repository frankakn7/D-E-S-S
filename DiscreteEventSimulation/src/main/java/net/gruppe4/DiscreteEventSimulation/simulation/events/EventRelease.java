package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventRelease extends Event{
    public EventRelease(Operation operation) {
        super(operation);
    }
    public Operation getOperation() {
        return operation;
    }
    @Override
    public Pair<Integer, Event> getFollowingEvent() {
        return new Pair<Integer, Event>(0,new EventBegin(this.operation));
    }
}
