package main;

import java.util.UUID;

public abstract class ScannableItem implements Scannable {
	private UUID itemID;
	private UUID referencedItemID;
	
	public ScannableItem(UUID referencedItemID) {
		this.itemID = UUID.randomUUID();
		this.referencedItemID = referencedItemID;
	}
	public ScannableItem(UUID itemID, UUID referencedItemID) {
		this.itemID = itemID;
		this.referencedItemID = referencedItemID;
	}
	public UUID getItemID() {return this.itemID;}
	public UUID getReferencedItemID() {return this.referencedItemID;}
	public String toString() {return String.format("{\"ItemID\":\"%s\",\"ReferencedItemID\":\"%s\"}", this.itemID, this.referencedItemID);}
}
