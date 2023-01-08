package net.gruppe4.DiscreteEventSimulation.objects;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@Type(JsonType.class)
	@Column(columnDefinition = "JSON")
	private String planJson;

	private String name;

	protected Plan(){}

	public Plan(String name, String json) {
		this.name = name;
		this.planJson = json;
	}

	@Override
	public String toString() {
		return "Plan{" +
				"uuid='" + uuid + '\'' +
				", planJson='" + planJson + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public String getPlanJson() {
		return planJson;
	}
}