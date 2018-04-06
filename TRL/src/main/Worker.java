package main;

import java.util.UUID;

import com.google.gson.JsonParser;

public class Worker extends Person {
	private UUID workerID;
	public Worker (Person person) {
		super(person.getName());
		this.workerID = UUID.randomUUID();
	}
	public Worker (String jsonData) {
		super(new JsonParser().parse(jsonData).getAsJsonObject().get("Name").getAsString());
		this.workerID = UUID.fromString(new JsonParser().parse(jsonData).getAsJsonObject().get("WorkerID").getAsString());
	}
	public void scan(HandheldScanner scanner, ScannableItem item) {
		scanner.scan(item);
	}
	public String toString() {return String.format("{\"WorkerID\":%s,\"Name\":\"%s\"}",this.workerID,this.name);}
}
