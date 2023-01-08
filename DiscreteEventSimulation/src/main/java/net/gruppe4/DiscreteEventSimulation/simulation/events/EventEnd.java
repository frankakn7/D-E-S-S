package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventEnd extends Event{
    public EventEnd(Operation operation, Integer time) {
        super(operation, time);
        this.name = "E";
    }

    @Override
    public Event getFollowingEvent() {
        if(this.operation.hasSuccessor()){
            return new EventBegin(this.operation.getSuccessor(), this.time);
        }
        return new EventFinished(this.operation, this.time);
    }

    @Override
    public boolean isDoable() {
        return !(this.operation.machineIsBroken());
    }

    @Override
    public void executeSimulationStateUpdates() {
        this.operation.setMachineFree(true);
    }

}
