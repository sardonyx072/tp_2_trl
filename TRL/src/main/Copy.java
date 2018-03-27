package main;

import java.util.UUID;

public class Copy implements Scannable {
	private UUID copyID;
	private Book book;
	
	public Copy(Book book) {
		this.copyID = UUID.randomUUID();
		this.book = book;
	}
	public UUID getScannableID() {return this.copyID;}
	public UUID getContentID() {return this.copyID;}
	public Book getBook() {return this.book;}
	public String toString() {return "{" + this.copyID + "::" + this.book + "}";}
}
