package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;

public interface SimulationCaseService {
    public abstract SimulationCase createSimCase(Plan plan);
    public abstract SimulationCase getSimCase(String uuid);
    public abstract void saveSimCase(SimulationCase simCase);
    public abstract void runSimulation(String uuid);
    public abstract String getResults(String uuid);
    public abstract void setResultsAndSave(String uuid, String results);
}
