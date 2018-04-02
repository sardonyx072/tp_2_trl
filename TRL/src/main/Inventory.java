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
		String[] copyList = str.substring(str.indexOf("\"Inventory\":[")+13, str.indexOf("],")-1).split("},{");
		copyList[0] = copyList[0].substring(1);
		copyList[copyList.length-1] = copyList[copyList.length-1].substring(0, copyList[copyList.length-1].length()-1);
		String[] bookList = str.substring(str.lastIndexOf("\"Info\":[")+8, str.lastIndexOf("]}")-1).split("},{");
		bookList[0] = bookList[0].substring(1);
		bookList[bookList.length-1] = bookList[bookList.length-1].substring(0, bookList[bookList.length-1].length()-1);
		JsonObject i = JsonObject();
		return inventory;
	}
	public String toString() {return String.format("{\"Inventory\":[%s],\"Info\":[%s]}",
			String.join(",", (String[]) Arrays.asList(this.getCopies()).stream().map(copy -> copy.toString()).toArray()),
			String.join(",", (String[]) Arrays.asList(this.getBooks()).stream().map(book -> book.toString()).toArray())
	);}
}
