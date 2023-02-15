package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;
import net.gruppe4.DiscreteEventSimulation.objects.SimulationCase;
import net.gruppe4.DiscreteEventSimulation.repositories.PlanRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public String createSimCase(Plan plan) {
        SimulationCase simCase = simCaseService.createSimCase(plan);
        simCaseService.saveSimCase(simCase);
        simCaseService.setAndSaveStatus(simCase.getUuid(), 200);
        return simCase.getUuid();
    }

    @Override
    @Async
    public void startSimCase(String simCaseUuid){
        /*try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        simCaseService.runSimulation(simCaseUuid);
    }
}
