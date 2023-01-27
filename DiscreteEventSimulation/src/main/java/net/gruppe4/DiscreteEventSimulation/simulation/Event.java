package net.gruppe4.DiscreteEventSimulation.simulation;

public class Event {
    private EventType eventType;
    private Machine machine;
    private Operation operation;
    private Integer date;


    public Event(EventType eventType) {
        this.eventType = eventType;
        this.machine = null;
        this.operation = null;
    }

    public Event(EventType eventType, Machine machine, Operation operation) {
        this.eventType = eventType;
        this.machine = machine;
        this.operation = operation;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }
}
