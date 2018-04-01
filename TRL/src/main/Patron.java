package main;

public class Patron extends Person {
	private Card card;
	public Patron (String name) {
		super(name);
	}
	public Card getCard() {return this.card;}
	public void setCard(Card card) {this.card = card;}
}
