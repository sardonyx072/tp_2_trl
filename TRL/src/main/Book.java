package main;

import java.util.UUID;

public class Book {
	private UUID bookID;
	private String title;
	private String author;
	
	public Book (UUID bookID, String title, String author) {
		this.bookID = bookID;
		this.title = title;
		this.author = author;
	}
	public Book (String title, String author) {
		this(UUID.randomUUID(),title,author);
	}
	public UUID getBookID() {return this.bookID;}
	public String getTitle() {return this.title;}
	public String getAuthor() {return this.author;}
	public String toString() {return String.format("{\"BookID\":%s,\"Title\":\"%s\",\"Author\":\"%s\"}", this.bookID, this.title, this.author);}
}
