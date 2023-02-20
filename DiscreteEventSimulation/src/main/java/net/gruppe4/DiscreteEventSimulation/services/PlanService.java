package net.gruppe4.DiscreteEventSimulation.services;

import net.gruppe4.DiscreteEventSimulation.objects.Plan;

public interface PlanService {
    public abstract void savePlan(Plan plan);
    public abstract Plan createPlanFromJson(String json);
    public abstract Iterable<Plan> getPlans();
    public abstract Plan getPlanFromUuid(String uuid);
    public abstract long deletePlanByUuid(String uuid);
    public abstract String createSimCase(Plan plan, Integer numOfSimulations);
    public abstract void startSimCase(String uuid);
}
