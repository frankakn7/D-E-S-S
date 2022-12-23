package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;

import java.util.ArrayList;
import java.util.TreeMap;

public class TimeslotQueue {
    private TreeMap<Integer, ArrayList<Event>> timeslots = new TreeMap<Integer, ArrayList<Event>>();
    private TreeMap<Integer, ArrayList<Event>> eventLog = new TreeMap<Integer, ArrayList<Event>>();

    public TimeslotQueue() {    }

    public void insertEvent(int time, Event event) {
        if (!this.timeslots.containsKey(time)) this.timeslots.put(time, new ArrayList<Event>());

        this.timeslots.get(time).add(event);
    }

    public boolean isEmpty(){
        return this.timeslots.isEmpty();
    }

    public Event pollNextEvent(){
        return this.timeslots.firstEntry().getValue().remove(0);
    }

    public void postponeEvent(Event event) {
        ArrayList<Event> eventList = this.timeslots.firstEntry().getValue();
        eventList.add(event);
        this.timeslots.firstEntry().setValue(eventList);
        // Eventuell ein Denkfehler
    }
}
