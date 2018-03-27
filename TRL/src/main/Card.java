package main;

import java.util.UUID;

public class Card implements Scannable {
	private UUID cardNo;
	private Record record;
	
	public Card(Record record) {
		this.cardNo = UUID.randomUUID();
		this.record = record;
	}
	public UUID getScannableID() {return this.cardNo;}
	public UUID getContentID() {return this.record.getUUID();}
	public Record getRecord() {
		return this.record;
	}
	public String toString() {return "{" + this.cardNo + "::" + this.record.toString() + "}";}
}
