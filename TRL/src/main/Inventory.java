package main;

import com.google.gson.*;

import java.io.File;
import java.util.Arrays;
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
	
	public Inventory() {this.copies = new HashMap<UUID,Copy>();}
	public boolean hasCopy(UUID id) {return this.copies.containsKey(id);}
	public boolean hasCopy(UUID copyID, UUID bookID) {return this.copies.containsKey(copyID) && this.copies.get(copyID).getReferencedItemID().equals(bookID);}
	public void addCopy(Copy copy) {
		this.copies.put(copy.getItemID(),copy);
		LOGGER.info("Added copy to inventory: " + copy);
	}
	public Copy removeCopy(UUID id) {
		Copy copy = this.copies.remove(id);
		LOGGER.info("Removed copy from inventory: " + copy);
		return copy;
	}
	private Copy[] getCopies() {return this.copies.values().toArray(new Copy[this.copies.size()]);}
	private Book[] getBooks() {return this.copies.values().stream().map(copy -> copy.getBook()).distinct().toArray(Book[]::new);}
	public static Inventory parse(String str) {
		LOGGER.severe("LOADING INVENTORY");
		Inventory inventory = new Inventory();
		JsonObject jsonRead = null;
		try {
			jsonRead = new JsonParser().parse(str).getAsJsonObject();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		HashMap<UUID,Book> books = new HashMap<UUID,Book>();
		LOGGER.severe("LOADING INVENTORY - CREATED OBJECTS");
		for (JsonElement i : jsonRead.getAsJsonArray("Info")) {
			Book book = new Book(i.toString());
			books.put(book.getBookID(), book);
			LOGGER.severe("LOADED BOOK: " + book.toString());
		}
		for (JsonElement i : jsonRead.getAsJsonArray("Inventory")) {
			Copy copy = new Copy(i.toString());
			inventory.addCopy(copy);
			LOGGER.severe("LOADED COPY: " + copy.toString());
		}
		return inventory;
	}
	public String toString() {return String.format("{\"Inventory\":[%s],\"Info\":[%s]}",
			String.join(",", Arrays.asList(this.getCopies()).stream().map(copy -> copy.toString()).toArray(String[]::new)),
			String.join(",", Arrays.asList(this.getBooks()).stream().map(book -> book.toString()).toArray(String[]::new))
	);}
}
