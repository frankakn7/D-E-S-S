package net.gruppe4.DiscreteEventSimulation.simulation;

import com.google.common.base.Strings;

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

    @Override
    public String toString() {
        String res = "";
        if (this.date != null) res += " " + Strings.padEnd(this.date.toString(), 5, ' ');
        res += Strings.padEnd(this.eventType.name(), 20, ' ');
        if (this.machine != null) res += " " + Strings.padEnd(this.machine.toString(), 5, ' ');
        if (this.operation != null) res += " " + Strings.padEnd(this.operation.toString(), 5, ' ');
        return res;
    }
}