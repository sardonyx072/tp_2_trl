package main;

import java.io.File;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class System {
	private static Logger LOGGER = null;
	static {
		try {
			LogManager.getLogManager().readConfiguration(System.class.getClassLoader().getResourceAsStream("main/resources/logging.properties"));
			File logdir = new File(LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern")).getParentFile();
			if (logdir != null) logdir.mkdir();
			LOGGER = Logger.getLogger(System.class.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	private HandheldScanner scanner;
	private Worker worker;
	private Record current;
	
	public System () {
		this.scanner = new HandheldScanner();
		this.worker = null;
	}
	public boolean signIn(Worker worker) {
		if (this.worker==null) {
			this.worker = worker;
			LOGGER.info("Signed in worker: " + worker);
			return true;
		}
		else {
			LOGGER.info("Could not sign in worker (worker already signed in): " + worker + ", " + this.worker);
			return false;
		}
	}
	public boolean startCheckout(Card card) {
		this.scanner.scan(card);
		// TODO
	}
}
