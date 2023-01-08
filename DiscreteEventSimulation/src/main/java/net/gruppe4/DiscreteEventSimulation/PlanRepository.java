package net.gruppe4.DiscreteEventSimulation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanRepository extends CrudRepository<Plan,String>{
    Plan findByUuid(String uuid);

}
