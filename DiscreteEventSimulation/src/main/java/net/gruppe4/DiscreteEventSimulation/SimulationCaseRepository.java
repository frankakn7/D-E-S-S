package net.gruppe4.DiscreteEventSimulation;

import org.springframework.data.repository.CrudRepository;

public interface SimulationCaseRepository extends CrudRepository<SimulationCase,String> {
    SimulationCase findByUuid(String uuid);
}
