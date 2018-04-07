package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class TRLApp {

	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.attachScanner(new HandheldScanner());
		Worker worker = new Worker(new Person("Eyad Shesli"));
		Patron patron1 = new Patron(new Person("Mitchell Hoffmann"));
		patron1.setCard(computer.createRecord(patron1));
		computer.addHold(patron1.getCard().getReferencedItemID(),new Hold("Has not paid tuition...ever."));
		Patron patron2 = new Patron(new Person("Sir Mitchell Hoffmann II Esq."));
		patron2.setCard(computer.createRecord(patron2));
		Patron patron3 = new Patron(new Person("HRH Mitchell Hoffmann III"));
		patron3.setCard(computer.createRecord(patron3));
		List<Book> books = new ArrayList<Book>();
		HashSet<Copy> copies = new HashSet<Copy>();
		List<Copy> checkout = new ArrayList<Copy>();
		books.add(new Book("From Hello World to Taking Over The World: An Idiot's Guide to Computers","Hugh Mann"));
		books.add(new Book("Silicon and Sparks, A Mahine Learning To Love", "Mr. Roboto"));
		books.add(new Book("Alan's Guide to Mythical Creatures Volume 17 - The Good Manager", "Alan Kay"));
		books.add(new Book("'Testing in Production' and Other Spooky Stories", "Martin Fowler")); //quotes are horrible in json, maybe use a jsonwriter?
		books.add(new Book("The Machine Cycle", "Not a Bike Enthusiast"));
		books.add(new Book("The Post-in Note Apocalypse", "Henry Gantt"));
		books.add(new Book("Hitchiker's Guide to Cyberspace", "Tim Berners-Lee"));
		books.add(new Book("1001 Popcorn Recipes That Penguins Will Love", "Linus Torvalds"));
		books.add(new Book("Lorem Ipsum", "Sample Text"));
		books.add(new Book("Who is JSON and why does he need so much REST?", "Jason Webster"));
		books.add(new Book("I Can C Clearly Now", "Dennis Ritchie"));
		books.add(new Book("I, Human", "Alan Turing and Isaac Asimov"));
		books.add(new Book("Singularity", "John von Neumann"));
		books.add(new Book("The Shortest Path to Success", "Edsger Dijkstra"));
		books.add(new Book("Daily Rituals that Keep Doctors Away", "Woz"));
		books.add(new Book("The Missing Link", "Nully Pointersson"));
		books.add(new Book("Creating the Perfect System", "CLU"));
		books.add(new Book("Goblins, COBOLs, and More: A Bestiary in Binary", "Wizards of the Code"));
		books.add(new Book("'Register? I Barely Know Her!' A Comedy Playbook", "Julian Assange"));
		books.add(new Book("All of Your Data Are Belong To Us", "Mark Zuckerberg"));
		books.add(new Book("FAT! A case study on the supersized file systems of America", "Ronald McData"));
		books.add(new Book("Skunk: A Tale of Two Dice", "Charles Dictens"));
		books.add(new Book("Old Chuck Babbage Who Always Smelled Like Cabbage", "Mother Google & Grimm"));
		books.add(new Book("The Legend of Antikythera", "Endiana Joins"));
		books.add(new Book("Finish the Game", "Commander Sark"));
		books.add(new Book("Cute Cat Videos In Text Form", "u/LoLsOrAnDoM"));
		books.add(new Book("An Elephant Never Forgets", "Disk non Volatile"));
		books.add(new Book("The Garbage Collection", "Anonymous Users from 4chan"));
		books.add(new Book("The Petty Arguments of Programmers and Why You're Wrong", "Formatter Shmormatter"));
		books.add(new Book("Borg Cube model 4F92AC8F Maintenance Manual (Simplified) Volume 536 of 76920", "The Borg Queen"));
		books.add(new Book("The Noble 8-bit Path and Samsara is a Circular Buffer", "Byte Buddha"));
		books.add(new Book("C Double-Plus Good: A Brave New Hello World", "Aldous Muxley"));
		books.add(new Book("JK Flip Flop! Pranks and Riddles", "S. D. Ram"));
		books.add(new Book("String.format('%s',pun);", "The Pun Machine"));
		books.add(new Book("Plagiarism", "Stackoverflow"));
		books.add(new Book("Pun Parity Parody - A Reflection on Bad Computer-Based Humor", "Y'hors Trully"));
		books.add(new Book("Exceptional: How To Make Errors and Still Succeed - A Self-Help Guide", "Try Catch Finally"));
		books.add(new Book("Make-Your-Own Sassy AI", "Tony Snark"));
		books.add(new Book("Emoticons and Decepticons, What's the Difference?", "Optimus Prime"));
		books.add(new Book("sudo cheat", "Kirito"));
		books.add(new Book("We Are Anonymous, We Are Legion", "A Bunch of Children"));
		books.add(new Book("Ghost in the Machine", "It's Actually Me, I'm Dead and is this Heaven?"));
		books.add(new Book("Foo, Bar, Baz", "temp"));
		books.add(new Book("Why Black Mirror is a Prescient Documentary of Humanity", "Waldo The Bear (\u2605 4.937)"));
		books.add(new Book("Who cares about ordinary Internet users, anyway?", "Ajit Pai"));
		books.add(new Book("Lose 280 Braincells or Less at a Time", "Orange Twitter Man"));
		books.add(new Book("Ghandi Has Declared Nuclear War", "Sid Meier"));
		books.add(new Book("How About A Nice Game of Chess?", "WOPR"));
		books.add(new Book("What is Wikipedia?", "Watson"));
		books.add(new Book("Bicentennial Man", "Isaac Asimov and Robin Williams"));
		books.add(new Book("Why Can't Future Families Just Be Normal?", "Rosie the Maid"));
		books.add(new Book("My Purpose is to Pass the Butter - Memoirs of an Almost-Useless Robot", "A Rick Sanchez Original Creation #C137-8491"));
		books.add(new Book("Let's-a Go!", "It's-a me, Mario"));
		books.add(new Book("why are Siri, Alexa, and Cortana So Special?", "Google Assistant"));
		books.add(new Book("Onion Routing the *.onion of The Onion on Onion Routing, Route N", "Tor"));
		books.add(new Book("Do The Dishes Already!", "Stack Pointer"));
		books.add(new Book("C Sharp, Not C Flat - A Programmer's Guide to Algo-Rhythms", "Database Clef"));
		books.add(new Book("How To Hack the CIA Mainframe in Under 60 Seconds", "Hank 'Teevee' McHackermann"));
		for (Book book: books) {
			int num = (int)(Math.random()*4)+1;
			for (int i = 0; i < num; i++)
				copies.add(new Copy(book));
		}
		if (computer.getCopies().size() == 0)
			for (Copy copy : copies)
				computer.addCopyToInventory(copy);
		copies.clear();
		computer.signIn(worker);
		Scanner scan = new Scanner(System.in);
		String line = null;
		int width = 4;
		List<Copy> inventory = computer.getCopies();
		inventory.sort((copy1, copy2) -> copy1.getBook().getTitle().compareTo(copy2.getBook().getTitle()));
		do {
			System.out.println("Inventory:");
			System.out.println("\t" + String.format("%"+width+"s. %s", "0", "<proceed to checkout>"));
			for (int i = 0; i < inventory.size(); i++) {
				Copy copy = inventory.get(i);
				System.out.println("\t" + String.format("%"+width+"s. Title: \"%s\", Author: \"%s\", Condition: \"%s\"", i+1, copy.getBook().getTitle(), copy.getBook().getAuthor(), copy.getCondition()));
			}
			System.out.println("Copies to Check Out:");
			if (checkout.size() == 0)
				System.out.println("\t<none>");
			for (int i = 0; i < checkout.size(); i++) {
				Copy copy = checkout.get(i);
				System.out.println("\t" + String.format("%"+width+"s. Title: \"%s\", Author: \"%s\", Condition: \"%s\"", i+1, copy.getBook().getTitle(), copy.getBook().getAuthor(), copy.getCondition()));
			}
			System.out.print("Check out another copy? Enter copy num or 0 to quit: ");
			line = scan.nextLine();
			try {
				Copy copy = inventory.get(Integer.parseInt(line)-1);
				if (copies.add(copy))
					checkout.add(copy);
			} catch (Exception e) {
				if (line.equals("0"))
					line = null;
			}
		} while (line != null);
		worker.scan(computer.getScanner(), patron1.getCard());
		if (computer.getCurrentRecord() != null) {
			for (int i = 0; i < checkout.size(); i++) {
				Copy copy = checkout.get(i);
				System.out.print("Confirm scan: " + String.format("%"+width+"s. Title: \"%s\", Author: \"%s\", Condition: \"%s\"", i+1, copy.getBook().getTitle(), copy.getBook().getAuthor(), copy.getCondition()) + "? ");
				scan.nextLine();
				worker.scan(computer.getScanner(), copy);
			}
			List<Copy> checkoutPost = computer.completeCheckout();
			if (checkoutPost.size()>0)
				System.out.println("Successfully checked out books");
			else
				System.out.print("Unsuccessful in checking out books");
		}
		else
			System.out.println("Invalid ID card.");
		scan.close();
	}
}
