package net.gruppe4.DiscreteEventSimulation.simulation.events;

import org.javatuples.Pair;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import java.util.ArrayList;

public abstract class Event {
    protected String id;
    protected Operation operation;

    public Event(Operation operation) {
        this.operation = operation;
    }

    public void getSimulationStateUpdates() {
        return;
    }

    public boolean isDoable() {
        return true;
    }

    /**
     * Overridable function that returns the event that must follow after this one
     * or null if there is none.
     * @return the event that must follow after this one or null
     */
    public Pair<Integer, Event> getFollowingEvent() {
        return null;
    }
}
