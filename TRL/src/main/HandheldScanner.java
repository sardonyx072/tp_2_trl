package main;

import java.io.File;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HandheldScanner implements Scannable {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(HandheldScanner.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(HandheldScanner.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private UUID scannableID;
	private UUID contentID;
	public HandheldScanner() {
		this.scannableID = null;
		this.contentID = null;
	}
	public void scan(Scannable item) {
		LOGGER.info("Scanned item ID: " + item.getScannableID() + ", with content ID: " + item.getContentID());
		LOGGER.info("Scanned item is: " + item.toString());
		this.scannableID = item.getScannableID();
		this.contentID = item.getContentID();
	}
	public UUID getScannableID() {return this.scannableID;}
	public UUID getContentID() {return this.contentID;}
	public void clearScannableID() {this.scannableID = null;}
	public void clearContentID() {this.contentID = null;}
}
