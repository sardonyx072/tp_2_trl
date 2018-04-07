package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RecordManager {
	private static final String RECORDS_DB = "./save/records.json";
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(RecordManager.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(RecordManager.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private HashMap<UUID, Record> records;
	
	public RecordManager() {this.records = new HashMap<UUID,Record>();}
	public boolean hasRecord(UUID id) {return this.records.containsKey(id);}
	public void addRecord(Record record) {
		this.records.put(record.getPatronID(), record);
		LOGGER.info("Added record to system: " + record);
	}
	public Record getRecord(UUID id) {return this.records.get(id);}
	public HashSet<Record> getRecords() {return new HashSet<Record>(this.records.values());}
	public void save() {
		BufferedWriter writer = null;
		File file = new File(RECORDS_DB);
		try {file.getParentFile().mkdir();} catch(Exception e) {}
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
		this.records = new HashMap<UUID,Record>();
		BufferedReader reader = null;
		File file = new File(RECORDS_DB);
		try {
			reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line = null;
			do {
				line = reader.readLine();
				if (line != null)
					builder.append(line);
			} while (line != null);
			JsonArray jsonRecords = new JsonParser().parse(builder.toString().trim()).getAsJsonObject().get("Records").getAsJsonArray();
			for (JsonElement jsonRecord : jsonRecords) {
				Record record = new Record(jsonRecord.toString());
				LOGGER.fine("Loaded record: " + record.toString());
				this.records.put(record.getPatronID(), record);
			}
			LOGGER.info("Succesfully loaded all records!");
		} catch (Exception e) {
			LOGGER.warning("Could not load patron records.");
			LOGGER.warning(e.getMessage());
			this.records = new HashMap<UUID,Record>();
		}
	}
	public String toString() {
		return String.format("{\"Records\":[%s]}", String.join(",", this.records.values().stream().map(record -> record.toString()).toArray(String[]::new)));
	}
}
