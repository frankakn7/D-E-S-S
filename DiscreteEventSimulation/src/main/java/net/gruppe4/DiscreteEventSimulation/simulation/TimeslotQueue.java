package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;
import org.javatuples.Pair;

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

    public Pair<Integer, Event> pollNextEvent(){
        return new Pair<Integer, Event>(this.timeslots.firstKey(),this.timeslots.firstEntry().getValue().remove(0));
    }

    public void postponeEvent(Event event) {
        ArrayList<Event> eventList = this.timeslots.firstEntry().getValue();
        eventList.add(event);
        this.timeslots.firstEntry().setValue(eventList);
        // Eventuell ein Denkfehler
    }

    public void logEvent(int time, Event event){
        if (!this.eventLog.containsKey(time)) this.eventLog.put(time, new ArrayList<Event>());

        this.eventLog.get(time).add(event);
    }
}
