package net.gruppe4.DiscreteEventSimulation.simulation;

import java.util.*;

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

    // Insert at at front of arraylist
    public void insertAtFront(Integer date, Event event) {
        if (!timeslots.containsKey(date)) {
            timeslots.put(date, new ArrayList<Event>());
        }

        timeslots.get(date).add(0, event);
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
        if (this.timeslots.isEmpty()) return null;

        return this.timeslots.firstKey();
    }

    /**
     * Returns the second date in the list of timeslots. If the list is empty,
     * null is returned. If the list only has one element, the first date is
     * returned.
     *
     * @return the second date in the list of timeslots or the first date if
     *         there's only one or null if the list is empty
     */
    public Integer getSecondDate() {
        if (this.timeslots.isEmpty()) return null;

        if (this.timeslots.size() >= 2) {
            return this.timeslots.higherKey(this.timeslots.firstKey());
        }

        return this.getFirstDate();
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
        event.setDate(this.getFirstDate());
        this.timeslots.get(this.getFirstDate()).remove(0);

        if (this.timeslots.get(this.getFirstDate()).isEmpty())
            this.timeslots.remove(this.getFirstDate());

        return event;
    }

    /**
     * Retrieves the next Event without removing it from the queue. If the
     * queue is empty, returns null.
     *
     * @return the next Event, or null if the queue is empty.
     */
    public Event peekNextEvent() {
        if (this.timeslots.isEmpty()) return null;
        if (this.timeslots.get(this.timeslots.firstKey()).isEmpty()) return null;
        Event event = this.timeslots.get(this.getFirstDate()).get(0);
        event.setDate(this.getFirstDate());
        return event;
    }


    /**
     * Poll an {@link Event} object from the {@link TimeslotQueue}, if there is
     * one scheduled at the passed `date`. Returns null if no {@link Event}
     * object is scheduled for that `date`.
     *
     * @param date  `date` to check for {@link Event} objects in the machines
     *              queue.
     * @return      Next scheduled {@link Event} object at `date` with. its
     *              null if none is scheduled for that `date`.
     */
    public Event pollNextEventIfDate(Integer date) {
        Event event = null;

        if (date.equals(this.getFirstDate())) {
            event = this.pollNextEvent();
        }

        return event;
    }

    /**
     * Returns the next event if it occurs on the specified date without
     * removing it.
     *
     * @param date the date to check for the next event
     * @return the next event if it occurs on the specified date, null
     *         otherwise
     */
    public Event peekEventIfDate(Integer date) {
        Event event = null;

        if (Objects.equals(date,this.getFirstDate())) {
            event = this.peekNextEvent();
        }
        return event;
    }

    /**
     * Pushes the entire Queue back by `time`. So if the Queue starts with 0
     * and you push it back by 10 time it will be starting at 10 with every
     * event having been pushed back by the same amount of time.
     *
     * @param time   The amount of `time` to push the queue back by
     */
    public void pushQueueBackByTime(Integer time) {
        TreeMap<Integer, ArrayList<Event>> newTimeslots = new TreeMap<Integer, ArrayList<Event>>();

        Iterator<Map.Entry<Integer, ArrayList<Event>>> i = this.timeslots.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<Integer, ArrayList<Event>> entry = i.next();
            newTimeslots.put(entry.getKey() + time, entry.getValue());
        }

        this.timeslots = newTimeslots;
    }
}
