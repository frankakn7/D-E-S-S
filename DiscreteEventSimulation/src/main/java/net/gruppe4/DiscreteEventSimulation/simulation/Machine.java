package net.gruppe4.DiscreteEventSimulation.simulation;


import java.util.Random;


public class Machine {
    private String id;
    private TimeslotQueue timeslotQueue;
    private Double brkdwnProb = 0.;
    private Double brkdwnLengthMean;
    private Double brkdwnLengthStandardDeviation;


    private Random generator;


    public Machine(String id, Double brkdwnProb, Double brkdwnLengthMean, Double brkdwnLengthStandardDeviation) {
        this.id = id;
        this.timeslotQueue = new TimeslotQueue();
        this.brkdwnProb = brkdwnProb;
        this.brkdwnLengthMean = brkdwnLengthMean;
        this.brkdwnLengthStandardDeviation = brkdwnLengthStandardDeviation;

        // TODO Set seed for randomgenerator
        this.generator = new Random();
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
        // TODO Implement releaseDate stuff
        Event begin = new Event(EventType.OPERATION_BEGIN, this, operation);
        Event end = new Event(EventType.OPERATION_END, this, operation);


        // TODO Check if last inserted timestamp is > than releasedate
        //  => if so append begin event to end of queue
        //  => else add begin event at releaseDate
        //  (due to dependencies releaseDate not important if earliest possible moment is after releasedate)
        //  => more of a integrity check

        Integer beginDate = operation.getReleaseDate();
        Integer releaseDate = operation.getReleaseDate();
        if (releaseDate != 0) {
            this.timeslotQueue.insert(releaseDate, begin);
        }
        else {
            beginDate = this.timeslotQueue.append(begin);
        }

        this.timeslotQueue.insert(beginDate + operation.getDuration(), end);
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

    public Integer getSecondEventDate() {
        if (!this.timeslotQueue.isEmpty()) {
            return this.timeslotQueue.getSecondDate();
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

    public Event peekEventIfDate(Integer date) {
        return this.timeslotQueue.peekEventIfDate(date);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public Boolean rollDiceForBreakdown() {
        if (this.brkdwnProb == 0.) return false;

        if (this.brkdwnProb >= this.generator.nextDouble()) {
            return true;
        }
        return false;
    }

    public Integer rollDiceForBreakdownLength() {
        Integer res = (int)Math.ceil(generator.nextGaussian(this.brkdwnLengthMean, this.brkdwnLengthStandardDeviation));
        //System.out.println(res);
        return res;
    }

    public void insertBreakdownAtFront() {
        Integer length = this.rollDiceForBreakdownLength();

        // TODO Maybe make operations optional, also check again if operations are required by any chance and if we
        //      can allow for operation to be null here
        Event begin = new Event(EventType.MACHINE_BREAKDOWN_BEGIN, this, null);
        Event end = new Event(EventType.MACHINE_BREAKDOWN_END, this, null);

        Integer beginDate = this.timeslotQueue.getFirstDate();
        this.timeslotQueue.pushQueueBackByTime(length);

        this.timeslotQueue.insert(beginDate, begin);
        this.timeslotQueue.insertAtFront(beginDate + length, end);
    }

    public void pushQueueBackByTime(Integer time) {
        this.timeslotQueue.pushQueueBackByTime(time);
    }

    public Boolean isTimeSlotQueueEmpty(){
        return this.timeslotQueue.isEmpty();
    }
}
