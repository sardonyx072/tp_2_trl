package main;

import java.io.File;
import java.util.HashSet;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Inventory {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(Inventory.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(Inventory.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private HashSet<Copy> copies;
	
	public Inventory() {
		this.copies = new HashSet<Copy>();
	}
	public void addCopy(Copy copy) {
		this.copies.add(copy);
		LOGGER.info("Added copy to inventory: " + copy);
	}
	public boolean hasCopy(Copy copy) {
		return this.copies.contains(copy);
	}
	public Copy remove(Copy copy) {
		if (this.copies.remove(copy)) {
			LOGGER.info("Removed copy from inventory: " + copy);
			return copy;
		}
		else {
			LOGGER.info("Could not remove copy from inventory (was not in inventory): " + copy);
			return null;
		}
	}
	public String toString() {return "{" + String.join("::",this.copies.stream().map(copy -> copy.toString()).toString()) + "}";}
}
