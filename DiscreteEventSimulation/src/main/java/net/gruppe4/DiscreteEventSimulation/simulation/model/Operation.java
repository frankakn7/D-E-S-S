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
        if(this.predecessor != null) {
            this.predecessor.setSuccessor(this);
        }
    }

    public Operation(Job job, Machine machine, Integer duration, Operation predecessor) {
        this.id = UUID.randomUUID().toString();
        this.job = job;
        this.machine = machine;
        this.duration = duration;
        this.predecessor = predecessor;
        if(this.predecessor != null) {
            this.predecessor.setSuccessor(this);
        }
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

    public String getMachineId(){return this.machine.getId();}

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Job getJob() {
        return job;
    }

    public String getJobId() {return this.job.getId();}

    public String getJobMachineCombinationId() {return this.getJobId() + this.getMachineId();}

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
        if (this.predecessor == null) return false;
        return true;
    }

    public boolean hasSuccessor(){
        if(this.successor != null) return true;
        return false;
    }

    public Integer getReleaseTime() {
        return this.job.getReleaseTime();
    }

    public boolean machineIsFree() {
        return this.machine.isFree();
    }

    public void setMachineFree(boolean free) {
        this.machine.setFree(free);
    }

    public boolean machineIsBroken(){
        return this.machine.isBroken();
    }

    public void setMachineBroken(boolean broken){
        this.machine.setBroken(broken);
    }

    public void setPredecessor(Operation predecessor) {
        this.predecessor = predecessor;
    }
}
