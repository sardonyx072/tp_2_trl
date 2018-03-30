package main;

import java.util.ArrayList;
import java.util.List;

public class TRLApp {

	public static void main(String[] args) {
		Computer computer = new Computer();
		Worker worker = new Worker("Eyad Shesli");
		Patron patron = new Patron("Mitchell Hoffmann");
		Record record = new Record(patron);
		record.addHold(new Hold("Has not paid tuition...ever."));
		computer.addRecord(record);
		patron.issueCard(new Card(record));
		HandheldScanner scanner = computer.getScanner();
		List<Book> books = new ArrayList<Book>();
		List<Copy> copies = new ArrayList<Copy>();
		List<Copy> checkout = new ArrayList<Copy>();
		books.add(new Book("From Hello World to Taking Over The World: An Idiot's Guide to Computers","Hugh Mann"));
		books.add(new Book("Silicon and Sparks", "Roboto Amor"));
		books.add(new Book("Alan's Guide to Mythical Creatures Volume 17 - The Good Manager", "Alan Kay"));
		books.add(new Book("\"Testing in Production\" and Other Spooky Stories", "Martin Fowler"));
		books.add(new Book("The Machine Cycle", "Not a Bike Enthusiast"));
		books.add(new Book("The Post-in Note Apocalypse", "Henry Gantt"));
		books.add(new Book("Lost in Cyberspace", "Tim Berners-Lee"));
		books.add(new Book("1001 Popcorn Recipes That Penguins Will Love", "Linus Torvalds"));
		for (Book book: books) {
			Copy first = new Copy(book);
			checkout.add(first);
			copies.add(first);
			copies.add(new Copy(book));
			copies.add(new Copy(book));
			copies.add(new Copy(book));
		}
		for (Copy copy : copies)
			computer.addCopy(copy);
		computer.signIn(worker);
		for (Copy copy : checkout)
			patron.acceptCopy(copy);
		patron.greet(worker);
		worker.greet(patron);
		worker.scan(scanner, patron.getCard());
		computer.startCheckout();
		while (patron.hasCopies()) {
			worker.acceptCopy(patron.giveCopy());
			worker.scan(scanner, worker.giveCopy());
			computer.checkoutCopy();
		}
		computer.completeCheckout();
	}
}
