package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.objects.Status;
import org.json.JSONArray;

public interface SimulationCaseService {
    public abstract SimulationCase createSimCase(Plan plan);
    public abstract SimulationCase getSimCaseById(String uuid);
    public abstract JSONArray getSimCasesJson();
    public abstract void saveSimCase(SimulationCase simCase);
    public abstract void runSimulation(String uuid);
    public abstract String getResults(String uuid);
    public abstract void setResultsAndSave(String uuid, String results);
    public abstract Status getStatus(String uuid);
    public abstract void setAndSaveStatus(String uuid, int numOfSimulations);
}
