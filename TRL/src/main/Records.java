package main;

import java.util.HashMap;
import java.util.UUID;

public class Records {
	private HashMap<UUID,Record> records;
	public Records () {
		this.records = new HashMap<UUID,Record>();
	}
	public boolean hasRecord (UUID id) {
		return this.records.containsKey(id);
	}
	public Record getRecord (UUID id) {
		return this.records.get(id);
	}
	public boolean addRecord (Record record) {
		if (!this.hasRecord(record.getUUID())) {
			this.records.put(record.getUUID(), record);
			return true;
		}
		return false;
	}
}
