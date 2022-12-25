package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;
import net.gruppe4.DiscreteEventSimulation.simulation.events.EventRelease;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.javatuples.Pair;

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
        this.setUpFirstEvents();
        while(!this.timeslotQueue.isEmpty()) {
            Pair<Integer, Event> eventAndTime = this.timeslotQueue.pollNextEvent();
            if (!eventAndTime.getValue1().isDoable()) {
                this.timeslotQueue.postponeEvent(eventAndTime.getValue1());
                continue;
            }
            //tried using generic values and lists to find what to update but much too complex
            eventAndTime.getValue1().executeSimulationStateUpdates();
            Pair<Integer, Event> nextEventAndTime = eventAndTime.getValue1().getFollowingEvent();
            this.timeslotQueue.insertEvent(nextEventAndTime.getValue0(), nextEventAndTime.getValue1());
            //add event-time and event itself to eventLog
            this.timeslotQueue.logEvent(eventAndTime.getValue0(),eventAndTime.getValue1());
            // TODO: Check if this is correct and run test
        }
    }
}
