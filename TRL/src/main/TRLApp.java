package main;

import java.util.ArrayList;
import java.util.List;

public class TRLApp {

	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.attachScanner(new HandheldScanner());
		Worker worker = new Worker(new Person("Eyad Shesli"));
		Patron patron1 = new Patron(new Person("Mitchell Hoffmann"));
		patron1.setCard(computer.createRecord(patron1));
		computer.addHold(patron1.getCard().getReferencedItemID(),new Hold("Has not paid tuition...ever."));
		Patron patron2 = new Patron(new Person("Mitchell 2 Hoffmann"));
		patron2.setCard(computer.createRecord(patron2));
		List<Book> books = new ArrayList<Book>();
		List<Copy> copies = new ArrayList<Copy>();
		List<Copy> checkoutPre = new ArrayList<Copy>();
		books.add(new Book("From Hello World to Taking Over The World: An Idiot's Guide to Computers","Hugh Mann"));
		books.add(new Book("Silicon and Sparks", "Roboto Amor"));
		books.add(new Book("Alan's Guide to Mythical Creatures Volume 17 - The Good Manager", "Alan Kay"));
		books.add(new Book("'Testing in Production' and Other Spooky Stories", "Martin Fowler")); //quotes are horrible in json, maybe use a jsonwriter?
		books.add(new Book("The Machine Cycle", "Not a Bike Enthusiast"));
		books.add(new Book("The Post-in Note Apocalypse", "Henry Gantt"));
		books.add(new Book("Lost in Cyberspace", "Tim Berners-Lee"));
		books.add(new Book("1001 Popcorn Recipes That Penguins Will Love", "Linus Torvalds"));
		books.add(new Book("Lorem Ipsum", "Sample Text"));
		books.add(new Book("Who is JSON and why does he need so much REST?", "Jason Webster"));
		books.add(new Book("I Can C Clearly Now", "Dennis Ritchie"));
		books.add(new Book("I, Human", "Alan Turing and Isaac Asimov"));
		books.add(new Book("Singularity", "John von Neumann"));
		books.add(new Book("The Shortest Path to Success", "Edsger Dijkstra"));
		books.add(new Book("Daily Rituals that Keep Doctors Away", "Woz"));
		books.add(new Book("The Missing Link", "Nully Pointersson"));
		for (Book book: books) {
			Copy first = new Copy(book);
			checkoutPre.add(first);
			copies.add(first);
			copies.add(new Copy(book));
			copies.add(new Copy(book));
			copies.add(new Copy(book));
		}
		for (Copy copy : copies)
			computer.addCopyToInventory(copy);
		computer.signIn(worker);
		worker.scan(computer.getScanner(), patron1.getCard());
		while (checkoutPre.size() > 0)
			worker.scan(computer.getScanner(), checkoutPre.remove(0));
		List<Copy> checkoutPost = computer.completeCheckout();
	}
}
