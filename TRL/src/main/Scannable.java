package main;

import java.util.UUID;

public interface Scannable {
	public UUID getItemID();
	public UUID getReferencedItemID();
}
