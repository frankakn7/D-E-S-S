package net.gruppe4.DiscreteEventSimulation.simulation.events;

import org.javatuples.Pair;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public class EventBegin extends Event{

    public EventBegin(Operation operation, Integer time) {
        super(operation, time);
        this.name = "B";
    }

    @Override
    public Event getFollowingEvent() {
        return new EventEnd(this.operation,this.time+operation.getDuration());
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
