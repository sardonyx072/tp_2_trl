package main;

import java.io.File;
import java.util.UUID;
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
	public UUID scan(Scannable item) {
		LOGGER.info("Scanned item ID: " + item.getScannableID() + ", with content ID: " + item.getContentID());
		LOGGER.info("Scanned item is: " + item.toString());
		return item.getContentID();
	}
}
