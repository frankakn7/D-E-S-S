package net.gruppe4.DiscreteEventSimulation.repositories;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan,String>{
    Plan findByUuid(String uuid);

}
