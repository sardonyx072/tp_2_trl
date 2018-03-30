package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Person {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(Person.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(Person.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private String name;
	protected List<Copy> copies;
	
	public Person(String name) {
		this.name = name;
		this.copies = new ArrayList<Copy>();
	}
	public String getName() {return this.name;}
	public void greet(Person person) {
		LOGGER.info(this.name + " said hello to " + person.getName());
	}
	public boolean hasCopies() {return this.copies.size() > 0;}
	public Copy giveCopy() {return this.copies.remove(this.copies.size()-1);}
	public void acceptCopy(Copy copy) {this.copies.add(copy);}
}
