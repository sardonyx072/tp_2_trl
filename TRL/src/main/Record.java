package main;

import java.io.File;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Record {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(Record.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(Record.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private UUID uuid;
	private Patron patron;
	private HashSet<Hold> holds;
	
	public Record(Patron patron) {
		this.uuid = UUID.randomUUID();
		this.patron = patron;
		this.holds = new HashSet<Hold>();
	}
	public UUID getUUID() {return this.uuid;}
	public Patron getPatron() {return this.patron;}
	public HashSet<Hold> getHolds() {return this.holds;}
	public void addHold(Hold hold) {
		this.holds.add(hold);
		LOGGER.info("Added hold on to account: " + hold + ", " + this.toString());
	}
	public Hold removeHold(Hold hold) {
		if (this.holds.remove(hold)) {
			LOGGER.info("Removed hold from account: " + hold + ", " + this.toString());
			return hold;
		}
		else {
			LOGGER.info("Failed to remove hold from account (doesn't exist): " + hold + ", " + this.toString());
			return null;
		}
	}
	public String toString() {
		return "{"
				+this.uuid
				+"::"+String.format("%-32s",this.patron.getName())
				+"::{"+String.join("::", (String[])this.holds.stream().map(hold -> hold.toString()).toArray())
				+"}}";
	}
}
