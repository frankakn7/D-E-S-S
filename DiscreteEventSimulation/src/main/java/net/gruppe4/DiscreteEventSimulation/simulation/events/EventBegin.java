package net.gruppe4.DiscreteEventSimulation.simulation.events;

import org.javatuples.Pair;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Triplet;

import java.util.ArrayList;

public class EventBegin extends Event{

    public EventBegin(Operation operation) {
        super(operation);
    }

    @Override
    public Pair<Integer, Event> getFollowingEvent() {
        return new Pair<Integer, Event>(operation.getDuration(), new EventEnd(this.operation));
    }

    @Override
    public boolean isDoable() {
        return operation.machineIsFree();
    }

    @Override
    public void executeSimulationStateUpdates() {
        this.operation.setMachineFree(false);
    }
}
