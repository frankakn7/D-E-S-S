package net.gruppe4.DiscreteEventSimulation.simulation.model;

public class Machine {
    private String id;
    private boolean isFree = true;
    private boolean isBroken = false;
    public Machine(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }
}
