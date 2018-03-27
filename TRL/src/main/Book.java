package main;

import java.util.UUID;

public class Book {
	private UUID bookID;
	private String title;
	private String author;
	
	public Book (String title, String author) {
		this.bookID = UUID.randomUUID();
		this.title = title;
		this.author = author;
	}
	public UUID getBookID() {return this.bookID;}
	public String getTitle() {return this.title;}
	public String getAuthor() {return this.author;}
	public String toString() {return "{" + this.bookID + "::" + String.format("%-32s",this.title) + "::" + String.format("%-32s",this.author) + "}";}
}
