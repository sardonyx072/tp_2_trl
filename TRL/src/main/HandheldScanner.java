package main;

import java.io.File;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HandheldScanner {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(HandheldScanner.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(HandheldScanner.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private Computer computer;
	public HandheldScanner() {
		this.computer = null;
	}
	public void attach(Computer computer) {this.computer = computer;}
	public void scan(ScannableItem item) {
		LOGGER.info("Scanning item with handheld scanner: " + item);
		this.computer.scan(item.getClass().toString(), item.getItemID(), item.getReferencedItemID());
	}
}
