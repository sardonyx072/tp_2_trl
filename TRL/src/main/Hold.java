package main;

import java.util.UUID;

public class Hold {
	private UUID holdID;
	private String type;
	
	public Hold(String type) {
		this.holdID = UUID.randomUUID();
		this.type = type;
	}
	public UUID getHoldID() {return this.holdID;}
	public String getType() {return this.type;}
	public String toString() {return "{" + this.holdID + "::" + this.type + "}";}
}
