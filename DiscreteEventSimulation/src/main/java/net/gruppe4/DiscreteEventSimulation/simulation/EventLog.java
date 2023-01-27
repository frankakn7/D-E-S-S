package net.gruppe4.DiscreteEventSimulation.simulation;

public class EventLog {
    private TimeslotQueue log;

    public EventLog() {
        this.log = new TimeslotQueue();
    }

    public void append(Event event) {
        this.log.insert(event.getDate(), event);
    }
}
