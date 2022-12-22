package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;
import net.gruppe4.DiscreteEventSimulation.simulation.events.EventRelease;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<Operation> operations;

    private TimeslotQueue timeslotQueue;

    public Simulation(ArrayList<Operation> operations) {
        this.operations = operations;
        this.timeslotQueue = new TimeslotQueue();
    }

    private void setUpFirstEvents() {
        for (Operation op : this.operations) {
            if (!op.hasPredecessor()) continue;


            EventRelease e = new EventRelease(op);
            timeslotQueue.insertEvent(op.getReleaseTime(), e);
        }
    }

    private void simulationLoop() {
        while(!this.timeslotQueue.isEmpty()) {
            Event event = this.timeslotQueue.getNextEvent();
        }
    }
}
