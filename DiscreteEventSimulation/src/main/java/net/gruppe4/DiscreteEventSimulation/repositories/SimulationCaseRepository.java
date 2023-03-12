package net.gruppe4.DiscreteEventSimulation.repositories;

import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * extends the database functions of the simulation case repository
 */
public interface SimulationCaseRepository extends CrudRepository<SimulationCase,String> {
    SimulationCase findByUuid(String uuid);
    long deleteAllByPlanUuid(String uuid);
    long deleteByUuid(String uuid);
}
