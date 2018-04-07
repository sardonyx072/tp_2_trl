package main;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class InventoryManager {
	private static final String INVENTORY_DB = "./save/inventory.json";
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(InventoryManager.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(InventoryManager.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private HashMap<UUID,Copy> copies;
	
	public InventoryManager() {this.copies = new HashMap<UUID,Copy>();}
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
	public Copy getCopy(UUID id) {return this.copies.get(id);}
	public Copy[] getCopies() {return this.copies.values().toArray(new Copy[this.copies.size()]);}
	public void save() {
		BufferedWriter writer = null;
		File file = new File(INVENTORY_DB);
		try{file.getParentFile().mkdir();} catch(Exception e) {}
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(this.toString());
		} catch (IOException e) {LOGGER.warning(e.getMessage());}
		finally {
			try {
				writer.close();
			} catch (IOException e) {LOGGER.severe(e.getMessage());}
		}
	}
	public void load() {
		BufferedReader reader = null;
		File file = new File(INVENTORY_DB);
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line = null;
			do {
				line = reader.readLine();
				if (line != null)
					builder.append(line);
			} while (line != null);JsonObject jsonRead = null;
			jsonRead = new JsonParser().parse(builder.toString().trim()).getAsJsonObject();
			for (JsonElement i : jsonRead.getAsJsonArray("Inventory")) {
				Copy copy = new Copy(i.toString());
				LOGGER.fine("Loaded copy: " + copy);
				this.copies.put(copy.getItemID(), copy);
			}
			LOGGER.info("Successfully loaded inventory!");
		} catch (IOException e) {
			LOGGER.warning("Could not load inventory.");
			LOGGER.warning(e.getMessage());
			this.copies = new HashMap<UUID,Copy>();
		} 
	}
	public String toString() {
		return String.format("{\"Inventory\":[%s]}",
			String.join(",", Arrays.asList(this.getCopies()).stream().map(copy -> copy.toString()).toArray(String[]::new))
	);}
}
