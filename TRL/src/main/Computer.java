package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Computer {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(Computer.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(Computer.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private HandheldScanner scanner;
	private Records records;
	private Inventory inventory;
	private Worker worker;
	private Record current;
	private List<Copy> copies;
	
	public Computer () {
		this.scanner = new HandheldScanner();
		this.records = new Records();
		this.inventory = new Inventory();
		this.worker = null;
		this.current = null;
		this.copies = null;
	}
	public boolean signIn(Worker worker) {
		if (this.worker==null) {
			this.worker = worker;
			LOGGER.info("Worker ID validation is skipped due to being out of scope.");
			LOGGER.info("Signed in worker: " + worker.getName());
			return true;
		}
		else {
			LOGGER.info("Could not sign in worker (worker already signed in): " + worker + ", " + this.worker);
			return false;
		}
	}
	public Record getRecord(UUID id) {return this.records.getRecord(id);}
	public void addRecord(Record record) {this.records.addRecord(record);}
	public Copy removeCopy(UUID id) {return this.inventory.removeCopy(id);}
	public void addCopy(Copy copy) {this.inventory.addCopy(copy);}
	public HandheldScanner getScanner() {return this.scanner;}
	public Record getCurrentRecord() {return this.current;}
	public boolean startCheckout() {
		UUID scannableID = this.scanner.getScannableID();
		this.scanner.clearScannableID();
		UUID contentID = this.scanner.getContentID();
		this.scanner.clearContentID();
		if (this.current==null && scannableID!=null && contentID!=null && this.records.hasRecord(contentID)) {
			LOGGER.info("Starting checkout for: " + this.records.getRecord(contentID).toString());
			this.current = this.records.getRecord(contentID);
			this.copies = new ArrayList<Copy>();
			return true;
		}
		else {
			LOGGER.info("Scan of ID card failed.");
			return false;
		}
	}
	public boolean checkoutCopy() {
		UUID scannableID = this.scanner.getScannableID();
		this.scanner.clearScannableID();
		UUID contentID = this.scanner.getContentID();
		this.scanner.clearContentID();
		if (this.current!=null && scannableID!=null && contentID!=null && this.inventory.hasCopy(contentID)) {
			LOGGER.info("Checking out copy for: " + this.inventory.getCopy(contentID).toString());
			this.copies.add(this.inventory.removeCopy(contentID));
			return true;
		}
		else {
			LOGGER.info("Scan of ID card failed.");
			return false;
		}
	}
	public List<Copy> completeCheckout() {
		List<Copy> copies = this.copies;
		for (Copy copy : this.copies)
			this.current.addCheckedOutCopy(this.inventory.removeCopy(copy.getContentID()));
		this.copies = null;
		this.current = null;
		return copies;
	}
}
