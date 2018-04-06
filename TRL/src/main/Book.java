package main;

import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Book {
	private UUID bookID;
	private String title;
	private String author;
	
	public Book (String title, String author) {
		this.bookID = UUID.randomUUID();
		this.title = title;
		this.author = author;
	}
	public Book (String jsonData) {
		JsonObject jsonBook = new JsonParser().parse(jsonData).getAsJsonObject();
		this.bookID = UUID.fromString(jsonBook.get("BookID").getAsString());
		this.title = jsonBook.get("Title").getAsString();
		this.author = jsonBook.get("Author").getAsString();
	}
	public UUID getBookID() {return this.bookID;}
	public String getTitle() {return this.title;}
	public String getAuthor() {return this.author;}
	public String toString() {return String.format("{\"BookID\":\"%s\",\"Title\":\"%s\",\"Author\":\"%s\"}", this.bookID, this.title, this.author);}
}
