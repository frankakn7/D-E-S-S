package net.gruppe4.DiscreteEventSimulation.simulation.model;

import java.util.UUID;

public class Operation {
    protected String id;
    private Job job;
    private Machine machine;
    private Operation successor;
    private Operation predecessor;

    private Integer duration;

    public Operation(Job job, Machine machine, Integer duration, String id, Operation predecessor) {
        this.id = id;
        this.job = job;
        this.machine = machine;
        this.duration = duration;
        this.predecessor = predecessor;
    }

    public Operation(Job job, Machine machine, Integer duration, Operation predecessor) {
        this.id = UUID.randomUUID().toString();
        this.job = job;
        this.machine = machine;
        this.duration = duration;
        this.predecessor = predecessor;
        this.predecessor.setSuccessor(this);
    }

    public void setSuccessor(Operation successor) {
        this.successor = successor;
    }

    public Operation getSuccessor() {
        return successor;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return this.job.getId() + "/" + this.machine.getId();
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean hasPredecessor() {
        if (this.predecessor != null) return true;
        return false;
    }

    public boolean hasSuccessor(){
        if(this.successor != null) return true;
        return false;
    }

    public Integer getReleaseTime() {
        return this.job.getReleaseTime();
    }
}
