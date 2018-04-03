package main;

import java.util.UUID;

public class Copy extends ScannableItem {
	private Book book;
	
	public Copy(UUID itemID, Book book) {
		super(itemID,book.getBookID());
	}
	public Copy(Book book) {
		super(book.getBookID());
		this.book = book;
	}
	public Book getBook() {return this.book;}
	public String toString() {return String.format("{\"Type\":\"Copy\",\"ScanInfo\":%s}", super.toString());}
}
