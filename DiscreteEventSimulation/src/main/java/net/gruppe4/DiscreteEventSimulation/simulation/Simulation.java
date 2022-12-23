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
            Event event = this.timeslotQueue.pollNextEvent();
            if (!event.isDoable()) {
                this.timeslotQueue.postponeEvent(event);
                continue;
            }

            //Event nextEvent = event.getFollowingEvent();
            // TODO: Continue here

            /**
             * 1.Update simulation state
             * 2.Get following events from current event
             * 3.Insert following events in EventQueue
             * 4.Move Event from EvetnQueue to EventLog
             */
        }
    }
}
