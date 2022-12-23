package net.gruppe4.DiscreteEventSimulation.simulation.events;

import org.javatuples.Pair;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public class EventBegin extends Event{

    public EventBegin(Operation operation) {
        super(operation);
    }

    @Override
    public Pair<Integer, Event> getFollowingEvent() {
        return new Pair<Integer, Event>(operation.getDuration(), new EventEnd(this.operation));
    }
}
