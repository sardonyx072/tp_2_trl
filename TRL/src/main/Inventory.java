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
	private Book[] getBooks() {return (Book[]) this.copies.values().stream().map(copy -> copy.getBook()).distinct().toArray();}
	public static Inventory parse(String str) {
		Inventory inventory = new Inventory();
		JsonObject jsonRead = new JsonParser().parse(str).getAsJsonObject();
		HashMap<UUID,Book> books = new HashMap<UUID,Book>();
		for (JsonElement i : jsonRead.getAsJsonArray("Info")) {
			JsonObject jsonBook = i.getAsJsonObject();
			Book book = new Book(UUID.fromString(jsonBook.get("BookID").getAsString()),jsonBook.get("Title").getAsString(),jsonBook.get("Author").getAsString());
			books.put(book.getBookID(), book);
		}
		for (JsonElement i : jsonRead.getAsJsonArray("Inventory")) {
			JsonObject jsonCopy = i.getAsJsonObject().getAsJsonObject("ScanInfo");
			inventory.addCopy(new Copy(UUID.fromString(jsonCopy.getAsJsonObject("ItemID").getAsString()),books.get(UUID.fromString(jsonCopy.getAsJsonObject("ReferencedItemID").getAsString()))));
		}
		return inventory;
	}
	public String toString() {return String.format("{\"Inventory\":[%s],\"Info\":[%s]}",
			String.join(",", (String[]) Arrays.asList(this.getCopies()).stream().map(copy -> copy.toString()).toArray()),
			String.join(",", (String[]) Arrays.asList(this.getBooks()).stream().map(book -> book.toString()).toArray())
	);}
}
