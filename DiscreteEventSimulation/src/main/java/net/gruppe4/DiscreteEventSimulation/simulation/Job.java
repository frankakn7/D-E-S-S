package net.gruppe4.DiscreteEventSimulation.simulation;

public class Job {
    public String id;
    public Integer releaseDate;
    public Integer dueDate;

    public Job(String id, Integer dueDate) {
        this.id = id;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
