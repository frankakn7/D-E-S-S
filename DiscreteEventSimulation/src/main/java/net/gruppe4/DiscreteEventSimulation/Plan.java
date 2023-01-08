package net.gruppe4.DiscreteEventSimulation;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.json.JSONObject;
import org.json.JSONArray;

@Entity
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@Type(JsonType.class)
	@Column(columnDefinition = "JSON")
	private String planJson;
	/*String jobId;
	int release_date;
	int due_date;
	int priority;
	String machineId;
	Double breakdown_probability;
	int breakdown_duration;
	String operationsId;
	String machine_id;
	String job_id;
	String predecessor;
	int duration;*/

	private String name;

	protected Plan(){}

	public Plan(String name, String json) {
		this.name = name;
		this.planJson = json;
		/*JSONObject obj = new JSONObject(json);
		
		JSONArray jobArr = obj.getJSONArray("jobs");
		for (int i = 0; i < jobArr.length(); i++) {

		jobId = jobArr.getJSONObject(i).getString("id");
		release_date = Integer.parseInt(jobArr.getJSONObject(i).getString("release_date"));
		due_date = Integer.parseInt(jobArr.getJSONObject(i).getString("due_date"));
		priority = Integer.parseInt(jobArr.getJSONObject(i).getString("priority"));

		}

		JSONArray machinesArr = obj.getJSONArray("machines");
		for(int i = 0; i < machinesArr.length(); i++) {

			machineId = machinesArr.getJSONObject(i).getString("id");
			breakdown_probability = Double.parseDouble(machinesArr.getJSONObject(i).getString("breakdown_probability"));
			breakdown_duration = Integer.parseInt(machinesArr.getJSONObject(i).getString("breakdown_duration"));

		}

		JSONArray operationsArr = obj.getJSONArray("operations");
		for(int i = 0; i < operationsArr.length(); i++) {

			operationsId = operationsArr.getJSONObject(i).getString("id");
			machine_id = operationsArr.getJSONObject(i).getString("machine_id");
			job_id = operationsArr.getJSONObject(i).getString("job_id");
			predecessor = operationsArr.getJSONObject(i).getString("predecessor");
			duration = Integer.parseInt(operationsArr.getJSONObject(i).getString("duration"));

		}*/
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