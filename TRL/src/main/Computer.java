package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Computer {
	private static final String INVENTORY_DB = "./save/inventory.txt";
	private static final String RECORDS_DB = "./save/records.txt";
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(Computer.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(Computer.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private UUID computerID;
	private HandheldScanner scanner;
	private HashMap<UUID,Record> records;
	private Inventory inventory;
	private Worker worker;
	private Record current;
	private List<Copy> copies;
	
	public Computer () {
		this.computerID = UUID.randomUUID();
		this.scanner = null;
		this.records = new HashMap<UUID,Record>();
		this.loadInventory();
		this.worker = null;
		this.current = null;
		this.copies = null;
	}
	public HandheldScanner getScanner() {return this.scanner;}
	public void attachScanner(HandheldScanner scanner) {
		scanner.attach(this);
		this.scanner = scanner;
	}
	public HashMap<Hold,Date> getHolds() {return this.current.getHolds();}
	public HashMap<Copy,Date> getOutCopies() {return this.current.getCopies();}
	public HashMap<Copy,Date> getAuditTrait() {return this.getAuditTrait();}
	public List<Copy> getRequestedCopies() {return this.copies;}
	public void signIn(Worker worker) {
		this.worker = worker;
		LOGGER.info("Signed in worker: " + worker.getName());
	}
	public void scan(String type, UUID itemID, UUID referencedItemID) {
		switch(type) {
		case "main.Card":
			if (this.records.get(referencedItemID).validate(itemID, referencedItemID))
				this.startCheckout(referencedItemID);
			else
				LOGGER.info("Session already open.");
			break;
		case "main.Copy":
			if (this.inventory.hasCopy(itemID,referencedItemID))
				this.checkoutCopy(itemID);
			else
				LOGGER.info("Scanned item is not in system inventory.");
			break;
		default:
			LOGGER.warning("Invalid type scanned. (" + type + ")");
			break;
		}
	}
	public Card createRecord(Patron patron) {
		Record record = new Record(patron);
		Card card = new Card(record);
		record.setCard(card);
		this.records.put(record.getPatronID(), record);
		return card;
	}
	public Card issueCard(Record record) {
		Card card = new Card(record);
		record.setCard(card);
		return card;
	}
	public void addCopyToInventory(Copy copy) {this.inventory.addCopy(copy);}
	public void addHold(UUID patronID, Hold hold) {this.records.get(patronID).addHold(hold);}
	public Record getCurrentRecord() {return this.current;}
	public void startCheckout(UUID recordID) {
		this.current = this.records.get(recordID);
		this.copies = new ArrayList<Copy>();
		LOGGER.info(String.format("Started checkout for patron ID %s on computer %s by worker %s.",this.current.getPatronID(), this.computerID, this.worker));
	}
	public void checkoutCopy(UUID copyID) {
		Copy copy = this.inventory.removeCopy(copyID);
		this.copies.add(copy);
		LOGGER.info(String.format("Added copy %s to running checkout list for patron %s.", copy, this.current.getPatronID()));
	}
	public List<Copy> completeCheckout() {
		List<Copy> copies = this.copies;
		LOGGER.info(String.format("Completed checkout for patron ID %s on computer %s by worker %s with copies %s.", 
			this.current.getPatronID(),
			this.computerID,
			this.worker,
			String.join(",", copies.stream().map(copy -> copy.toString()).toArray(String[]::new))
		));
		this.copies = null;
		this.current = null;
		this.saveInventory();
		return copies;
	}
	private void saveInventory() {
		BufferedWriter writer = null;
		File file = new File(INVENTORY_DB);
		try{file.getParentFile().mkdir();} catch(Exception e) {}
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(this.inventory.toString());
		} catch (IOException e) {LOGGER.warning(e.getMessage());}
		finally {
			try {
				writer.close();
			} catch (IOException e) {LOGGER.severe(e.getMessage());}
		}
	}
	private void loadInventory() {
		BufferedReader reader = null;
		File file = new File(INVENTORY_DB);
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line = null;
			do {
				line = reader.readLine();
				builder.append(line);
			} while (line != null);
			this.inventory = Inventory.parse(builder.toString());
		} catch (IOException e) {
			LOGGER.warning("Could not load inventory.");
			this.inventory = new Inventory();
		}
	}
	private void saveRecords() {
		
	}
	private void loadRecords() {
		
	}
}
