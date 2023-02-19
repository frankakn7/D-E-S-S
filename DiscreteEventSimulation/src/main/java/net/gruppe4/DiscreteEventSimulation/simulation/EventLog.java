package net.gruppe4.DiscreteEventSimulation.simulation;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Stores a log of {@link Event} objects that occur during the simulation
 * process and keeps track of them by appending them to multiple list types for
 * ease of access in different situations (plain {@link ArrayList}, a
 * {@link Machine} to {@link ArrayList} mapping and a {@link TimeslotQueue}.
 *
 * Additionally, provides methods for retrieving the log and maps, checking if
 * a machine breakdown is currently ongoing and checking if an operation has
 * finished.
 */
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

    public ArrayList<Event> getArrayList() {
        return this.log;
    }

    public HashMap<Machine, ArrayList<Event>> getMachineLogMap() {
        return machineLogMap;
    }

    public ArrayList<Event> getMachineLog(Machine m) {
        return this.machineLogMap.get(m);
    }


    /**
     * Iterates through current log beginning from the end to check if a
     * machine breakdown is currently ongoing
     *
     * @param machine  The {@link Machine} object to check in for breakdowns.
     * @return         true if breakdown is ongoing, false if not.
     */
    public Boolean isMachineBreakdownOpen(Machine machine) {
        if (!this.machineLogMap.containsKey(machine)) return false;

        ArrayList<Event> machineLog = this.machineLogMap.get(machine);
        ListIterator<Event> i = machineLog.listIterator(machineLog.size());
        while (i.hasPrevious()) {
            Event e = i.previous();
            if (e.getEventType() == EventType.MACHINE_BREAKDOWN_END) return false;
            if (e.getEventType() == EventType.MACHINE_BREAKDOWN_BEGIN) return true;
        }

        return false;
    }

    /**
     * Checks if a operation passed by an {@link Operation} object is currently
     * ongoing.
     *
     * @param op  {@link Operation} object to check if finished
     * @return    true if {@link Operation} object has not finished else false
     */

    // TODO HARIS HIER WEITERARBEITEN
    public Boolean hasOperationFinished(Operation op) {
        ListIterator<Event> i = this.log.listIterator(this.log.size());;

        while (i.hasPrevious()) {
            Event e = i.previous();
            if (op == e.getOperation()) {
                switch (e.getEventType()) {
                    case OPERATION_BEGIN -> { return false; }
                    case OPERATION_END -> { return true; }
                }
            }

        }

        return false;
    }
}
