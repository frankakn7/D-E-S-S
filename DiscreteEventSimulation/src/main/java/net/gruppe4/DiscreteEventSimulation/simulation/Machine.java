package net.gruppe4.DiscreteEventSimulation.simulation;


import java.util.Objects;
import java.util.Random;

/**
 * Machine class representing a production resource. Responsible for managing
 * the queue of events associated with the machine and simulating machine
 * breakdowns. Also accepts operations and converts them into corresponding
 * events, which are added to the machines {@link TimeslotQueue} object.
 */
public class Machine {
    private String id;
    private TimeslotQueue timeslotQueue;
    private Double brkdwnProb = 0.;
    private Double brkdwnLengthMean;
    private Double brkdwnLengthStandardDeviation;
    private Double repairCostPerTime;
    private Double costPerTime;


    private Random generator;


    public Machine(String id, Double brkdwnProb, Double brkdwnLengthMean, Double brkdwnLengthStandardDeviation, Double repairCostPerTime, Double costPerTime) {
        this.id = id;
        this.repairCostPerTime = repairCostPerTime;
        this.costPerTime = costPerTime;
        this.timeslotQueue = new TimeslotQueue();
        this.brkdwnProb = brkdwnProb;
        this.brkdwnLengthMean = brkdwnLengthMean;
        this.brkdwnLengthStandardDeviation = brkdwnLengthStandardDeviation;

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
        Event begin = new Event(EventType.OPERATION_BEGIN, this, operation);
        Event end = new Event(EventType.OPERATION_END, this, operation);


        // Vary Operations duration
        if (operation.rollDiceForDurVariation()) {
            operation.setDuration(operation.rollDiceForVariedDurationLength());
        }

        Integer beginDate = operation.getReleaseDate();
        Integer releaseDate = operation.getReleaseDate();

        beginDate = this.timeslotQueue.append(begin);

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

    /**
     * Returns the `date` of the second event in Queue/next event that is bound
     * to be processed without doing any processing.
     *
     * @return   `date` of the second next {@link Event} object in Queue.
     */
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

    /**
     * Peek/Get a reference to an {@link Event} object from the
     * {@link TimeslotQueue}, without taking it out of the queue. If there is
     * one scheduled at the passed `date`. Returns null if no {@link Event}
     * object is scheduled for that `date`.
     *
     * @param date  `date` to check for {@link Event} objects in the machines
     *              queue.
     * @return      Next scheduled {@link Event} object at `date`. null if none
     *              is scheduled for that `date`.
     */
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

    /**
     * Simulates a dice roll to determine if a breakdown occurs using gaussian
     * distribution.
     *
     * @return true if a breakdown occurs, false otherwise.
     */
    public Boolean rollDiceForBreakdown() {
        if (Objects.equals(this.brkdwnProb, 0.)) return false;

        if (this.brkdwnProb >= this.generator.nextDouble()) {
            return true;
        }
        return false;
    }

    /**
     * Rolls a dice to determine the length of a breakdown based on the
     * specified mean and standard deviation using gaussian distribution.
     *
     * @return an integer representing the length of the breakdown, with a
     *         minimum value of 1
     */
    public Integer rollDiceForBreakdownLength() {
        Integer res = Math.max((int)Math.round(generator.nextGaussian(this.brkdwnLengthMean, this.brkdwnLengthStandardDeviation)),1);
        //System.out.println(res);
        return res;
    }

    /**
     * Inserts a breakdown event at the front of the timeslot queue.
     * The length of the breakdown event is determined by calling the {@link #rollDiceForBreakdownLength()} method.
     * The breakdown event consists of a {@link EventType#MACHINE_BREAKDOWN_BEGIN} event and a corresponding
     * {@link EventType#MACHINE_BREAKDOWN_END} event.
     */
    public void insertBreakdownAtFront() {
        Integer length = this.rollDiceForBreakdownLength();

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

    public Double getRepairCostPerTime() {
        return repairCostPerTime;
    }

    public Double getCostPerTime() {
        return costPerTime;
    }
}
