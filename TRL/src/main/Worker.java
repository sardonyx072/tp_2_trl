package main;

import java.util.UUID;

public class Worker extends Person {
	private UUID workerID;
	public Worker (String name) {
		super(name);
		this.workerID = UUID.randomUUID();
	}
	public void scan(HandheldScanner scanner, ScannableItem item) {
		scanner.scan(item);
	}
	public String toString() {return String.format("{\"WorkerID\":%s,\"Name\":%s}",this.workerID,this.name);}
}
