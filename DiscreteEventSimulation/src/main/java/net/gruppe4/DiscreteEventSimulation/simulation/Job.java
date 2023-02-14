package net.gruppe4.DiscreteEventSimulation.simulation;

public class Job {
    public String id;
    private Integer releaseDate;
    private Integer dueDate;
     Double costPerLatenessTime;

    public Job(String id, Integer dueDate, Double costPerLatenessTime) {
        this.id = id;
        this.dueDate = dueDate;
        this.costPerLatenessTime = costPerLatenessTime;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public Integer getDueDate() {
        return dueDate;
    }

    public String getId() {
        return id;
    }

    public Double getCostPerLatenessTime() {
        return costPerLatenessTime;
    }
}
