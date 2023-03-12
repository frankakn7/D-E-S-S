package net.gruppe4.DiscreteEventSimulation.objects;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Represents the Uploaded plan objects. Contains a name, the plan JSON and a Timestamp for when it was created
 */
@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@Type(JsonType.class)
	@Column(columnDefinition = "JSON")
	private String planJson;

	private String name;

	private Timestamp createdOn;

	protected Plan(){}

	public Plan(String name, String json) {
		this.name = name;
		this.planJson = json;
	}

	/**
	 * give timestamp to plan when its created
	 */
	@PrePersist
	protected void onCreate(){
		this.createdOn = new Timestamp(System.currentTimeMillis());
	}

	@Override
	public String toString() {
		return "Plan{" +
				"uuid='" + uuid + '\'' +
				", planJson='" + planJson + '\'' +
				", name='" + name + '\'' +
				", createdOn='" + createdOn + '\'' +
				'}';
	}

	/**
	 * transfroms plan into a json object
	 * @return a {@link JSONObject}
	 */
	public JSONObject toJsonObject(){
		JSONObject planJsonObject = new JSONObject();
		planJsonObject.put("uuid",this.uuid);
		planJsonObject.put("name",this.name);
		planJsonObject.put("planJson",this.planJson);
		planJsonObject.put("createdOn",this.createdOn);
		return planJsonObject;
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public String getPlanJson() {
		return this.planJson;
	}

	public Timestamp getCreatedOn(){ return this.createdOn;}
}