package main;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
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
	private HashMap<UUID,Copy> copies;
	
	public Inventory() {
		this.copies = new HashMap<UUID,Copy>();
	}
	public void addCopy(Copy copy) {
		this.copies.put(copy.getContentID(),copy);
		LOGGER.info("Added copy to inventory: " + copy);
	}
	public boolean hasCopy(UUID id) {
		return this.copies.containsKey(id);
	}
	public Copy getCopy (UUID id) {
		return this.copies.get(id);
	}
	public Copy removeCopy(UUID id) {
		Copy copy = this.getCopy(id);
		if (copy!=null) {
			this.copies.remove(id);
			LOGGER.info("Removed copy from inventory: " + copy);
			return copy;
		}
		else {
			LOGGER.info("Could not remove copy from inventory (was not in inventory): " + copy);
			return null;
		}
	}
	public String toString() {return "{" + String.join("::",this.copies.values().stream().map(copy -> copy.toString()).toString()) + "}";}
}
