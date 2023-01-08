package net.gruppe4.DiscreteEventSimulation;

import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan,String>{
    Plan findByUuid(String uuid);

}
