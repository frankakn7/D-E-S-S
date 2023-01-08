package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventRelease extends Event{
    public EventRelease(Operation operation, Integer time) {
        super(operation, time);
        this.name = "R";
    }
    public Operation getOperation() {
        return operation;
    }
    @Override
    public Event getFollowingEvent() {
        return new EventBegin(this.operation,this.time);
    }

}
