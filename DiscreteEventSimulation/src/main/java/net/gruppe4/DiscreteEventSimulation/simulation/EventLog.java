package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.ArrayList;
import java.util.TreeMap;
import net.gruppe4.DiscreteEventSimulation.simulation.events.Event;

public class EventLog {

    private TreeMap<Integer, ArrayList<Event>> log = new TreeMap<Integer, ArrayList<Event>>();

    public EventLog() {

    }

    public void logEvent(int time, Event event) {
        if (!this.log.containsKey(time))
            this.log.put(time, new ArrayList<Event>());

        this.log.get(time).add(event);
    }

    public String printTimestampList(ArrayList<Event> eventList){
        String res = "";
        for (Event e : eventList){
            res += e + ",";
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "";

        for (Integer key : this.log.keySet()) {
            res += key + ": " + printTimestampList(this.log.get(key)) + "\n";
        }

        return res;
    }
}