package net.gruppe4.DiscreteEventSimulation.services;

import jakarta.transaction.Transactional;
import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.repositories.PlanRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service functions for everything to do with plans
 */
@Service
public class PlanServiceImpl implements PlanService{
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private SimulationCaseService simCaseService;

    @Override
    public void savePlan(Plan plan){
        planRepository.save(plan);
    }

    /**
     * Creates a new plan object
     * @param json - the plan in a JSON string format
     * @return - returns the newly created {@link Plan}
     */
    @Override
    public Plan createPlanFromJson(String json) {
        JSONObject obj = new JSONObject(json);
        return new Plan(obj.getString("name"), obj.getJSONObject("plan").toString());
    }

    @Override
    public Iterable<Plan> getPlans() {
        return planRepository.findAll();
    }

    @Override
    public Plan getPlanFromUuid(String uuid) {
        return planRepository.findByUuid(uuid);
    }

    @Override
    @Transactional
    public long deletePlanByUuid(String uuid){
        simCaseService.deleteSimCasesByPlanId(uuid);
        return planRepository.deleteByUuid(uuid);
    }

    /**
     * Creates a new Simulation Case from a plan
     * @param plan - the plan to be used for the simulation
     * @param numOfSimulations - number of simulation runs that should be executed
     * @return - the uuid of the simCase
     */
    @Override
    public String createSimCase(Plan plan, Integer numOfSimulations) {
        SimulationCase simCase = simCaseService.createSimCase(plan);
        simCaseService.saveSimCase(simCase);
        simCaseService.setAndSaveStatus(simCase.getUuid(), numOfSimulations);
        return simCase.getUuid();
    }

    /**
     * Starts a simulation
     * @param simCaseUuid - the uuid of the simulation to be started
     */
    @Override
    @Async
    public void startSimCase(String simCaseUuid){
        simCaseService.runSimulation(simCaseUuid);
    }
}
