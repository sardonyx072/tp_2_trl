package main;

public class Person {
	protected String name;
	public Person(String name) {this.name = name;}
	public String getName() {return this.name;}
	public String toString() {return String.format("{\"Name\":\"%s\"}", this.name);}
}
