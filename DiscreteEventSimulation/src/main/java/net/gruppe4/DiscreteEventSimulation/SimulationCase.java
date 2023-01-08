package net.gruppe4.DiscreteEventSimulation;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class SimulationCase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "planId")
    private Plan plan;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSON")
    private String resultJson;

    protected SimulationCase() {
    }

    public SimulationCase(Plan plan) {
        this.plan = plan;
    }

    public String getUuid() {
        return uuid;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }
}
