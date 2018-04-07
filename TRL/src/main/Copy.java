package main;

import java.util.UUID;

import com.google.gson.JsonParser;

public class Copy extends ScannableItem {
	private Book book;
	private Condition condition;

	public Copy (Book book) {
		super(book.getBookID());
		this.book = book;
		this.condition = Condition.NEW;
	}
	public Copy (String jsonData) {
		super(UUID.fromString(new JsonParser().parse(jsonData).getAsJsonObject().get("ScanInfo").getAsJsonObject().get("ItemID").getAsString()),UUID.fromString(new JsonParser().parse(jsonData).getAsJsonObject().get("ScanInfo").getAsJsonObject().get("ReferencedItemID").getAsString()));
		this.book = new Book(new JsonParser().parse(jsonData).getAsJsonObject().get("BookInfo").toString());
		this.condition = Condition.valueOf(new JsonParser().parse(jsonData).getAsJsonObject().get("Condition").getAsString());
	}
	public Book getBook() {return this.book;}
	public Condition getCondition() {return this.condition;}
	public void setCondition(Condition condition) {this.condition = condition;}
	public String toString() {return String.format("{\"Type\":\"Copy\",\"ScanInfo\":%s,\"BookInfo\":%s,\"Condition\":\"%s\"}", super.toString(), this.book.toString(), this.condition);}
}
