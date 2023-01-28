package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * TimeSlotQueue --- DataType storing a list of events happening in a given timeslot indexed by a `date`
 */
public class TimeslotQueue {
    private TreeMap<Integer, ArrayList<Event>> timeslots = new TreeMap<Integer, ArrayList<Event>>();


    /**
     * Inserts an `event` into the timeslot `date`. Appends it to the end of
     * the timeslots event list.
     *
     * @param date   the date to insert the event at
     * @param event  the event to insert
     */
    public void insert(Integer date, Event event) {
        // Instantiate a timeslot (ArrayList) if there is none
        if (!timeslots.containsKey(date)) {
            timeslots.put(date, new ArrayList<Event>());
        }

        timeslots.get(date).add(event);
    }

    /**
     * Append a passed {@link Event} object to the end of the last timeslot.
     * Creates a timeslot at `date` 0 if no timeslots have been instantiated
     * yet.
     *
     * @param event  the event to be appended at the end of the timeslotQueue
     * @return       the `date` of the timeslot the `event` got inserted in
     */
    public int append(Event event) {
        if (this.timeslots.size() == 0) {
            this.insert(0, event);
            return 0;
        }

        this.timeslots.get(this.timeslots.lastKey()).add(event);
        return this.timeslots.lastKey();
    }

    /**
     * Returns last {@link Event} object in queue without modifying the
     * timeslot queue.
     *
     * @return   Returns last {@link Event} object in queue or null if empty.
     */
    public Event getLastEvent() {
        if (this.timeslots.isEmpty()) return null;
        if (this.timeslots.get(this.timeslots.lastKey()).isEmpty()) return null;
        ArrayList<Event> timeslot = this.timeslots.get(this.timeslots.lastKey());
        return timeslot.get(timeslot.size() - 1);
    }

    /**
     * Returns true if this {@link TimeslotQueue} contains no {@link Event}
     * objects.
     *
     * @return   true if this {@link TimeslotQueue} contains no {@link Event}
     *           objects
     */
    public Boolean isEmpty() {
        if (this.timeslots.size() == 1) return this.timeslots.get(this.timeslots.firstKey()).isEmpty();

        return this.timeslots.isEmpty();
    }

    /**
     * Returns an {@link ArrayList} of all {@link Event} objects stored in
     * a timeslot. Returns null if no corresponding timeslot exists.
     *
     * @param date   Date of the requested timeslot
     * @return       {@link ArrayList} of {@link Event} objects in timeslot
     */
    public ArrayList<Event> getTimeslot(Integer date) {
        if (!this.timeslots.containsKey(date)) return null;

        return this.timeslots.get(date);
    }

    /**
     * Returns the first key/date that has a corresponding timeslot.
     * If no timeslot has yet been instantiated returns 0.
     *
     * @return   The first/date in the {@link TimeslotQueue}
     */
    public Integer getFirstDate() {
        if (this.timeslots.isEmpty()) return 0;

        return this.timeslots.firstKey();
    }

    /**
     * Polls first {@link Event} object in {@link TimeslotQueue}. This means it
     * returns it and removes it from the queue. If timeslot is empty removes
     * it as well.
     *
     * @return  First {@link Event} object in queue
     */
    public Event pollNextEvent() {
        if (this.timeslots.isEmpty()) return null;
        if (this.timeslots.get(this.timeslots.firstKey()).isEmpty()) return null;

        Event event = this.timeslots.get(this.getFirstDate()).get(0);
        this.timeslots.get(this.getFirstDate()).remove(0);

        if (this.timeslots.get(this.getFirstDate()).isEmpty())
            this.timeslots.remove(this.getFirstDate());

        return event;
    }


    /**
     * Poll an {@link Event} object from the {@link TimeslotQueue}, if there is
     * one scheduled at the passed `date`. Returns null if no {@link Event}
     * object is scheduled for that `date`. If event gets pulled its `date`
     * gets set to the passed `date`.
     *
     * @param date  `date` to check for {@link Event} objects in the machines
     *              queue.
     * @return      Next scheduled {@link Event} object at `date` with its
     *              `date` variable set to the passed `date`. null if none is
     *              scheduled for that `date`.
     */
    public Event pollNextEventIfDate(Integer date) {
        Event event = null;

        if (this.getFirstDate() == date) {
            event = this.pollNextEvent();

            // TODO Mention that this state change of event is happening in this method
            event.setDate(date);
        }

        return event;
    }
}
