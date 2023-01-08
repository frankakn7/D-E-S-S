package net.gruppe4.DiscreteEventSimulation;

public interface PlanService {
    public abstract void savePlan(Plan plan);
    public abstract Plan createPlanFromJson(String json);
    public abstract Iterable<Plan> getPlans();
    public abstract Plan getPlanFromUuid(String uuid);
    public abstract String createSimCase(Plan plan);
    public abstract void startSimCase(String uuid);
}
