package net.gruppe4.DiscreteEventSimulation.simulation.events;

import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

public class EventMachineFixed extends Event{
    public EventMachineFixed(Operation operation, Integer time) {
        super(operation, time);
        this.name = "MF";
    }

    @Override
    public void executeSimulationStateUpdates() {
        this.operation.setMachineBroken(false);
    }

}
