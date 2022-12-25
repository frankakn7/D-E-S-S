package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventEnd extends Event{
    public EventEnd(Operation operation) {
        super(operation);
    }

    @Override
    public Pair<Integer, Event> getFollowingEvent() {
        if(this.operation.hasSuccessor()){
            return new Pair<Integer, Event>(0,new EventBegin(this.operation.getSuccessor()));
        }
        return new Pair<Integer, Event>(0,new EventFinished(this.operation));
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
