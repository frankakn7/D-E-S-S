package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;
import net.gruppe4.DiscreteEventSimulation.simulation.events.EventRelease;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<Operation> operations;

    private TimeslotQueue timeslotQueue;
    private EventLog eventLog;

    public Simulation(ArrayList<Operation> operations) {
        this.operations = operations;
        this.timeslotQueue = new TimeslotQueue();
        this.eventLog = new EventLog();
    }

    private void setUpFirstEvents() {
        for (Operation op : this.operations) {
            if (op.hasPredecessor()) continue;

            EventRelease e = new EventRelease(op, op.getReleaseTime());
            this.timeslotQueue.insertEvent(op.getReleaseTime(), e);
        }
    }

    public EventLog simulationLoop() {
        this.setUpFirstEvents();
        Event currentEvent;
        while((currentEvent = this.timeslotQueue.pollNextEvent()) != null) {
            if (!currentEvent.isDoable()) {
                this.timeslotQueue.postponeEvent(currentEvent);
                continue;
            }
            currentEvent.executeSimulationStateUpdates();
            Event nextEvent = currentEvent.getFollowingEvent();
            if(nextEvent != null){
                this.timeslotQueue.insertEvent(nextEvent.getTime(), nextEvent);
            }
            this.eventLog.logEvent(currentEvent.getTime(), currentEvent);
        }
        return this.eventLog;
    }
}
