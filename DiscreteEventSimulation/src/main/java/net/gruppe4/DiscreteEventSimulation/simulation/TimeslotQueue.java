package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.TreeMap;

public class TimeslotQueue {
    private TreeMap<Integer, ArrayList<Event>> timeslots = new TreeMap<Integer, ArrayList<Event>>();

    public TimeslotQueue() {    }

    public void insertEvent(int time, Event event) {
        if (!this.timeslots.containsKey(time)) this.timeslots.put(time, new ArrayList<Event>());

        this.timeslots.get(time).add(event);
    }

    public boolean isEmpty(){
        return this.timeslots.isEmpty();
    }

    public Event pollNextEvent(){
        while(this.timeslots.firstEntry() != null) {
            Event nextEvent = this.timeslots.firstEntry().getValue().remove(0);
            if (this.timeslots.firstEntry().getValue().isEmpty()) {
                this.timeslots.pollFirstEntry();
            }
            return nextEvent;
        }
        return null;
    }

    public void postponeEvent(Event event) {
        ArrayList<Event> eventList = this.timeslots.firstEntry().getValue();
        event.setTime(this.timeslots.firstKey());
        eventList.add(event);
        this.timeslots.replace(this.timeslots.firstKey(),eventList);
        // Eventuell ein Denkfehler
    }

    public String printTimestampList(ArrayList<Event> eventList){
        String res = "";
        for (Event e : eventList){
            res += e + ",";
        }
        res += "\n";
        return res;
    }

    @Override
    public String toString() {
        String res = "";

        for (Integer key : this.timeslots.keySet()) {
            res += key + ": " + printTimestampList(this.timeslots.get(key));
        }

        return res;
    }
}
