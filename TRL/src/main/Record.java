package main;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
	private UUID patronID;
	private Patron patron;
	private Card card;
	private HashMap<Hold,Date> holds;
	private HashMap<Copy,Date> copies;
	private HashMap<Copy,Date> audit;
	
	public Record(Patron patron) {
		this.patronID = UUID.randomUUID();
		this.patron = patron;
		this.card = null;
		this.holds = new HashMap<Hold,Date>();
		this.copies = new HashMap<Copy,Date>();
		this.audit = new HashMap<Copy,Date>();
	}
	public UUID getPatronID() {return this.patronID;}
	public Patron getPatron() {return this.patron;}
	public HashMap<Hold,Date> getHolds() {return this.holds;}
	public HashMap<Copy,Date> getCopies() {return this.copies;}
	public HashMap<Copy,Date> getAudit() {return this.audit;}
	public void setCard(Card card) {this.card = card;}
	public boolean validate(UUID cardID, UUID patronID) {return this.card.getItemID().equals(cardID) && this.patronID.equals(patronID);}
	public void checkoutCopy(Copy copy) {
		int daysUntilDue = 120;
		LOGGER.info(String.format("Checking out copy %s and added to patron record %s, due in %s days.",copy,this.patronID,daysUntilDue));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, daysUntilDue);
		Date dueDate = calendar.getTime();
		this.copies.put(copy,dueDate);
		LOGGER.info(String.format("Successfully checked out copy %s and added to patron record %s, due on %s.",copy,this.patronID,dueDate));
	}
	public void returnCopy(Copy copy) {
		LOGGER.info(String.format("Returning copy %s and adding return date to patron record %s.", copy,this.patronID));
		Date returnDate = new Date();
		this.copies.remove(copy);
		this.audit.put(copy,returnDate);
		LOGGER.info(String.format("Successfully returned copy %s on %s and added to patron record %s.", copy, returnDate, this.patronID));
	}
	public void addHold(Hold hold) {
		LOGGER.info(String.format("Adding hold %s to patron record %s.",hold,this.patronID));
		Date date = new Date();
		this.holds.put(hold,date);
		LOGGER.info(String.format("Successfully added hold %s to patron record %s on %s.",hold,this.patronID,date));
	}
	public void removeHold(Hold hold) {
		LOGGER.info(String.format("Removing hold %s from patron record %s.",hold,this.patronID));
		Date addedDate = this.holds.remove(hold);
		Date date = new Date();
		LOGGER.info(String.format("Successfully removed hold %s added on %s from patron record %s on %s.",hold,addedDate,this.patronID,date));
	}
	public String toString() {
		return String.format("{\"PatronID\":%s,\"Name\":\"%s\",\"ActiveCard\":%s,\"Holds\":[%s],\"CheckedOutCopies\":[%s],\"AuditTrail\":[%s]}",
			this.patronID,
			this.patron.getName(),
			this.card,
			String.join(",", (String[]) this.holds.keySet().stream().map(hold -> String.format("{\"Hold\":%s,\"AddDate\":%s}",hold,this.holds.get(hold))).toArray()),
			String.join(",", (String[]) this.copies.keySet().stream().map(copy -> String.format("{\"Copy\":%s,\"DueDate\":%s}",copy,this.copies.get(copy))).toArray()),
			String.join(",", (String[]) this.audit.keySet().stream().map(copy -> String.format("{\"Copy\":%s,\"ReturnedDate\":%s}",copy,this.audit.get(copy))).toArray())
		);
	}
}
