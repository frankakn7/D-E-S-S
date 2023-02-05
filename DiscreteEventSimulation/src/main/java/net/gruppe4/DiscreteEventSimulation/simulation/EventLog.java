package net.gruppe4.DiscreteEventSimulation.simulation;

import java.lang.reflect.Array;
import java.util.*;

public class EventLog {
    private TimeslotQueue timeslots;
    private ArrayList<Event> log;
    private HashMap<Machine, ArrayList<Event>> machineLogMap;

    public EventLog() {
        // TODO consider using an ArrayList instead
        this.timeslots = new TimeslotQueue();
        this.log = new ArrayList<Event>();
        this.machineLogMap = new HashMap<Machine, ArrayList<Event>>();
    }

    /**
     * Appends a passed {@link Event} object to the log.
     *
     * @param event  {@link Event} object to append.
     */
    public void append(Event event) {
        this.timeslots.insert(event.getDate(), event);
        this.log.add(event);

        Machine m = event.getMachine();
        if (m != null) {
            if (!this.machineLogMap.containsKey(m)) this.machineLogMap.put(m, new ArrayList<Event>());
            this.machineLogMap.get(m).add(event);
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (Event e : this.log) {
            res += e.toString() + "\n";
        }
        return res;
    }

    // TODO Maybe write a unified interface for this
    public Boolean isMachineBreakdownOpen(Machine machine) {
        if (!this.machineLogMap.containsKey(machine)) return false;

        ArrayList<Event> machineLog = this.machineLogMap.get(machine);
        ListIterator<Event> i = machineLog.listIterator(machineLog.size());
        if (i.hasPrevious()) {
            Event e = i.previous();
            if (e.getEventType() == EventType.MACHINE_BREAKDOWN_END) return false;
            if (e.getEventType() == EventType.MACHINE_BREAKDOWN_BEGIN) return true;
        }

        return false;
    }
}
