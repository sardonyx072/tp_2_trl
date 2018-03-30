package main;

public class Worker extends Person {
	public Worker (String name) {
		super(name);
	}
	public void scan(HandheldScanner scanner, Scannable item) {
		scanner.scan(item);
	}
}
