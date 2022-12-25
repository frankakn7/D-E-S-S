package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventMachineBreakdown extends Event {
    public EventMachineBreakdown(Operation operation) {
        super(operation);
    }

    @Override
    public Pair<Integer, Event> getFollowingEvent() {
        //TODO: Change time to breakdown duration
        return new Pair<Integer, Event>(0, new EventMachineFixed(operation));
    }

    @Override
    public void executeSimulationStateUpdates() {
        this.operation.setMachineBroken(true);
    }
}
