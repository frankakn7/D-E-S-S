package net.gruppe4.DiscreteEventSimulation.simulation;


public class Machine {
    private String id;
    private TimeslotQueue timeslotQueue;

    public Machine(String id) {
        this.id = id;
        this.timeslotQueue = new TimeslotQueue();
    }

    /**
     * Takes in a {@link Operation} object, translates it to corresponding
     * {@link Event} objects, and appends them into the {@link Machine} objects
     * {@link TimeslotQueue}.
     *
     * Either puts the Begin {@link Event} at the {@link Operation} objects
     * releaseDate or, if there is no set releaseDate, puts them at the end of
     * the {@link TimeslotQueue}.
     *
     * @param operation  Operation to be translated to {@link Event} objects
     *                   and inserted
     */
    public void takeIn(Operation operation) {
        // TODO Implement releasedate stuff
        Event begin = new Event(EventType.OPERATION_BEGIN, this, operation);
        Event end = new Event(EventType.OPERATION_END, this, operation);

        // TODO Recheck this logic
        //      Should it really just check for != 0?
        Integer begin_date = operation.getReleaseDate();
        Integer releasedate = operation.getReleaseDate();
        if (releasedate != 0) {
            this.timeslotQueue.insert(releasedate, begin);
        }
        else {
            begin_date = this.timeslotQueue.append(begin);
        }

        this.timeslotQueue.insert(begin_date + operation.getDuration(), end);
    }

    /**
     * Returns the `date` of the first event in Queue/next event that is bound
     * to be processed without doing any processing.
     *
     * @return   `date` of the next {@link Event} object in Queue.
     */
    public Integer getNextEventDate() {
        if (!this.timeslotQueue.isEmpty()) {
            return this.timeslotQueue.getFirstDate();
        }

        return null;
    }

    /**
     * Poll an {@link Event} object from the {@link TimeslotQueue}, if there is
     * one scheduled at the passed `date`. Returns null if no {@link Event}
     * object is scheduled for that `date`.
     *
     * @param date  `date` to check for {@link Event} objects in the machines
     *              queue.
     * @return      Next scheduled {@link Event} object at `date`. null if none
     *              is scheduled for that `date`.
     */
    public Event pollEventIfDate(Integer date) {
        return this.timeslotQueue.pollNextEventIfDate(date);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
