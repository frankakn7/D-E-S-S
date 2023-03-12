package net.gruppe4.DiscreteEventSimulation.repositories;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import org.springframework.data.repository.CrudRepository;

/**
 * extending the database functions for the plan repository
 */
public interface PlanRepository extends CrudRepository<Plan,String>{
    Plan findByUuid(String uuid);

    long deleteByUuid(String uuid);
}
