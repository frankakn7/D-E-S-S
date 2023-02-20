package net.gruppe4.DiscreteEventSimulation.simulation;

import com.google.common.collect.FluentIterable;
import com.google.common.base.Predicate;
import io.hypersistence.utils.hibernate.type.basic.Inet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents one simulation run, handles the simulation in a core simulation
 * loop.
 */
public class Simulation {
    private EventLog eventLog;
    private Map<String, Machine> machines;

    public Simulation(Map<String, Machine> machines, ArrayList<Operation> operations) {
        this.machines = machines;
        this.eventLog = new EventLog();


        // Sort elements into their respective Machine queues
        for (Map.Entry<String, Machine> entry : machines.entrySet()) {
            Predicate<Operation> filterByMachine = operation -> operation.getMachine() == entry.getValue();
            var machinesOperations = new ArrayList<>(FluentIterable.from(operations).filter(filterByMachine).stream().toList());


            for (Operation op : this.sortMachinesOperations(machinesOperations)) {
                entry.getValue().takeIn(op);
            }
        }
    }


    /**
     * Takes in an {@link ArrayList} of {@link Operation} objects sitting in
     * the same queue and sorts it by looking up their machineQueuePredecessor.
     *
     * @param operations  Input List containing {@link Operation} objects
     *                    from the same Queue
     * @return            Sorted list of {@link Operation} objects.
     */
    private ArrayList<Operation> sortMachinesOperations(ArrayList<Operation> operations) {
        // Traverse the machines unsorted operations array and find the one
        // that's supposed to be the first in queue.
        ArrayList<Operation> ops = new ArrayList<>(operations);

        ArrayList<Operation> sortedOperations = new ArrayList<Operation>();
        Iterator<Operation> i = ops.iterator();
        while (i.hasNext()) {
            Operation operation = i.next();
            if (operation.hasNoMachineQueuePredecessor()) {
                sortedOperations.add(operation);
                i.remove();
                break;
            }
        }

        // Traverse the remaining List checking if the last entry in the sorted
        // one corresponds to the current elements machine queue predecessor
        while (!ops.isEmpty()) {
            i = ops.iterator();
            while (i.hasNext()) {
                Operation op = i.next();
                if (op.getMachineQueuePredecessor() == sortedOperations.get(sortedOperations.size() - 1)) {
                    sortedOperations.add(op);
                    i.remove();
                    break;
                }
            }
        }

        return sortedOperations;
    }

    /**
     * Actual simulation loop where all the simulation logic happens. Looks
     * through the {@link Machine} map for the next `date` an event is
     * scheduled, polls that event and puts it into the {@link EventLog}
     * object.
     *
     * @return  True if successful
     */
    private Boolean simulationLoop() {
        while (this.findNextEventDate() != null) {
            for (Map.Entry<String, Machine> entry : this.machines.entrySet()) {
                Integer nextDate = this.findNextEventDate();
                Machine m = entry.getValue();

                // CHECK FOR BREAKDOWNS
                if (!m.isTimeSlotQueueEmpty() && !this.eventLog.isMachineBreakdownOpen(m)) {
                    if (m.rollDiceForBreakdown()) {
                        m.insertBreakdownAtFront();
                    }
                }

                // CHECK IF EVENT DOABLE
                // Checks if there might be some conditional predecessors it has to wait for
                Event e = m.peekEventIfDate(nextDate);
                if (e == null) continue;
                if (e.getEventType() == EventType.OPERATION_BEGIN) {
                    if (!this.isOperationDoable(e.getOperation(), nextDate)) {
                        // Retrieve all other Operations on same date and check if at least one is doable
                        // if at least one is doable continue
                        // if none is doable or list is empty pushBackByTime

                        //if NO operation doable && operations list at date >= 2 then push back to AfterNextDate
                        //if operation NOT doable && operations list at date == 1 then push back to NextDate
                        if (this.checkIfDoableEventExistsAtDate(nextDate)) continue;
                        m.pushQueueBackByTime(this.findAfterNextEventDate(nextDate) - nextDate);
                        continue;
                    }

                }

                e = m.pollEventIfDate(nextDate);
                if (e != null) this.eventLog.append(e);
            }
        }
        return true;
    }

    /**
     * Checks if the given operation can be executed based on its conditional
     * predecessors and releaseDate.
     *
     * @param op the operation to check
     * @return true if the operation can be executed, false otherwise
     */
    private Boolean isOperationDoable(Operation op, int date) {
        Boolean res = true;

        if (op.getReleaseDate() > date) return false;
        if (op.getJob().getReleaseDate() > date) return false;

        if (op.getConditionalPredecessors() == null) return true;
        for (Operation pred : op.getConditionalPredecessors()) {
            if(!this.eventLog.hasOperationFinished(pred)) res = false;
        }

        return res;
    }

    /**
     * Function call to run the actual Simulation. Returns an {@link EventLog}
     * object if successful.
     *
     * @return  Log of all the events that happened during simulation.
     */
    public EventLog runSim() {
        Boolean wasSuccessful = this.simulationLoop();
        if (!wasSuccessful) {
            // TODO Throw error if simulation unsuccessful
        }

        return this.eventLog;
    }

    /**
     * Loops through the map of {@link Machine} objects to find the next `date`
     * an event is scheduled. Returns that `date` or null if the
     * {@link Machine} objects queues are empty.
     *
     * @return   Next scheduled event's `date` or null if all {@link Machine}
     *           objects have finished.
     */
    private Integer findNextEventDate() {
        ArrayList<Integer> dates = new ArrayList<Integer>();

        for (Map.Entry<String, Machine> entry : this.machines.entrySet()) {
            Integer date = entry.getValue().getNextEventDate();
            if (date == null) continue;

            dates.add(date);
        }
        if (dates.isEmpty()) return null;

        Collections.sort(dates);
        return dates.get(0);
    }

    /**
     * Finds the next date after the current date at which an event is
     * scheduled to occur across all machines. Only considers the first and
     * second event dates of each machine.
     *
     * @param currentDate the current date
     * @return the next date after the current date at which an event is
     *         scheduled to occur, or null if no such date exists
     */
    private Integer findAfterNextEventDate(Integer currentDate) {
        ArrayList<Integer> dates = new ArrayList<Integer>();

        for (Map.Entry<String, Machine> entry : this.machines.entrySet()) {
            Integer firstDate = entry.getValue().getNextEventDate();
            Integer secondDate = entry.getValue().getSecondEventDate();

            if (firstDate != null && !firstDate.equals(currentDate)) dates.add(firstDate);
            if (secondDate != null && !secondDate.equals(currentDate)) dates.add(secondDate);
        }
        if (dates.isEmpty()) return null;

        Collections.sort(dates);
        return dates.get(0);
    }

    /**
     * Returns a list of all events that are scheduled to occur on the given
     * date.
     *
     * @param date the date to check for scheduled events
     * @return an ArrayList of Event objects that are scheduled for the given
     *         date, empty if there are no events
     */
    // Retrieves all events from all machines that are in a certain timeslot
    private ArrayList<Event> peekAllEventsAtDate(Integer date) {
        ArrayList<Event> res = new ArrayList<Event>();

        for(Map.Entry<String, Machine> entry : this.machines.entrySet()) {
            Event e = entry.getValue().peekEventIfDate(date);
            if (e == null) continue;
            res.add(e);
        }

        return res;
    }

    /**
     * Checks if there is at least one event that is doable on the given date.
     * An event is considered doable if it is an operation begin event and the
     * operation it corresponds to is doable.
     *
     * @param date the date to check for doable events
     * @return true if there is at least one doable event at the given date,
     *         false otherwise
     */
    private Boolean checkIfDoableEventExistsAtDate(Integer date) {
        for(Event e : this.peekAllEventsAtDate(date)) {
            if (e.getEventType() != EventType.OPERATION_BEGIN) return true;
            if (this.isOperationDoable(e.getOperation(), date)) return true;
        }

        return false;
    }
}
