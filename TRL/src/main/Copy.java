package main;

import java.util.UUID;

import com.google.gson.JsonParser;

public class Copy extends ScannableItem {
	private Book book;

	public Copy (Book book) {
		super(book.getBookID());
		this.book = book;
	}
	public Copy (String jsonData) {
		super(UUID.fromString(new JsonParser().parse(jsonData).getAsJsonObject().get("ScanInfo").getAsJsonObject().get("ItemID").getAsString()),UUID.fromString(new JsonParser().parse(jsonData).getAsJsonObject().get("ScanInfo").getAsJsonObject().get("ReferencedItemID").getAsString()));
		this.book = new Book(new JsonParser().parse(jsonData).getAsJsonObject().get("BookInfo").toString());
	}
	public Book getBook() {return this.book;}
	public String toString() {return String.format("{\"Type\":\"Copy\",\"ScanInfo\":%s,\"BookInfo\":%s}", super.toString(), this.book.toString());}
}
