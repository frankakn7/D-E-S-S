package net.gruppe4.DiscreteEventSimulation.objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Represents the simulation cases that were executed.
 * Contains a uuid, the plan (plan uuid in Database), the results as JSON and a timestamp when it was created
 */
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

    private Timestamp createdOn;


    protected SimulationCase() {
    }

    @PrePersist
    protected void onCreate(){
        this.createdOn = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "SimulationCase{" +
                "uuid='" + uuid + '\'' +
                ", plan=" + plan +
                ", resultJson='" + resultJson + '\'' +
                '}';
    }

    public SimulationCase(Plan plan) {
        this.plan = plan;
/*        JSONObject resultsJson = new JSONObject();
        this.resultJson = new JSONObject().put("results",resultsJson.toString()).toString();*/

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

    public Timestamp getCreatedOn() {
        return createdOn;
    }
}
