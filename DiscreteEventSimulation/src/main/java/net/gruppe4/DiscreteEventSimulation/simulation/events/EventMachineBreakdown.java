package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

public class EventMachineBreakdown extends Event {
    public EventMachineBreakdown(Operation operation, Integer time) {
        super(operation, time);
        this.name = "MB";
    }

    @Override
    public Event getFollowingEvent() {
        //TODO: Change time to breakdown duration
        return new EventMachineFixed(this.operation, this.time);
    }

    @Override
    public void executeSimulationStateUpdates() {
        this.operation.setMachineBroken(true);
    }

}
