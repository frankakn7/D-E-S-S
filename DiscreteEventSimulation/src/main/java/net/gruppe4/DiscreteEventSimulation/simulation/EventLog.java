package net.gruppe4.DiscreteEventSimulation.simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventLog {
    private TimeslotQueue timeslots;
    private ArrayList<Event> log;

    public EventLog() {
        // TODO consider using an ArrayList instead
        this.timeslots = new TimeslotQueue();
        this.log = new ArrayList<Event>();
    }

    /**
     * Appends a passed {@link Event} object to the log.
     *
     * @param event  {@link Event} object to append.
     */
    public void append(Event event) {
        this.timeslots.insert(event.getDate(), event);
        this.log.add(event);
    }

    @Override
    public String toString() {
        String res = "";
        for (Event e : this.log) {
            res += e.toString() + "\n";
        }
        return res;
    }
}
