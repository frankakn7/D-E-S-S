package net.gruppe4.DiscreteEventSimulation.simulation.events;

public class Event {
    protected String id;
    protected Integer duration;

    public Event(Integer duration) {
        this.duration = duration;
    }
    public Event getFollowingEvent() {
        return null;
    }

    public void getSimulationStateUpdates() {
        return;
    }

}
