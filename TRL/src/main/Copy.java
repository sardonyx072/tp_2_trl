package main;

public class Copy extends ScannableItem {
	private Book book;
	
	public Copy(Book book) {
		super(book.getBookID());
		this.book = book;
	}
	public Book getBook() {return this.book;}
	public String toString() {return String.format("{\"Type\":\"Copy\",%s}", super.toString());}
}
