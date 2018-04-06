package main;

public class Hold {
	private String type;
	
	public Hold(String type) {
		this.type = type;
	}
	public String getType() {return this.type;}
	public String toString() {return String.format("{\"Type\":\"%s\"}", this.type);}
}
