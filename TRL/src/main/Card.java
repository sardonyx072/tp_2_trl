package main;

public final class Card extends ScannableItem {
	private Record record;
	
	public Card(Record record) {
		super(record.getPatronID());
		this.record = record;
	}
	public Record getRecord() {return this.record;}
	public String toString() {return String.format("{\"Type\":\"PatronCard\",%s}", super.toString());}
}
