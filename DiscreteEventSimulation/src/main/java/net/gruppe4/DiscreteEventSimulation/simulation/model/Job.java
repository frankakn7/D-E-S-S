package net.gruppe4.DiscreteEventSimulation.simulation.model;

public class Job {
    private final String id;
    private final Integer releaseTime;

    public Job(String id, Integer releaseTime) {
        this.id = id;
        this.releaseTime = releaseTime;
    }

    public String getId() {
        return id;
    }

    public Integer getReleaseTime() {
        return releaseTime;
    }

}
