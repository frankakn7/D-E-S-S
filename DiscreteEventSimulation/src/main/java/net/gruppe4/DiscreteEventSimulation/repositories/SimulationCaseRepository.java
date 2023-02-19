package net.gruppe4.DiscreteEventSimulation.repositories;

import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SimulationCaseRepository extends CrudRepository<SimulationCase,String> {
    SimulationCase findByUuid(String uuid);
    long deleteAllByPlanUuid(String uuid);
    //TODO implement delete by uuid in simulation case service and simulation case service impl
    long deleteByUuid(String uuid);
}
