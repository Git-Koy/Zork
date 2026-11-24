package ch.bbw.zork;

/**
 * Application entry point that creates and starts the Zork game.
 */
public class Zork2 {

	/**
	 * Starts the program by creating a new game and running its main loop.
	 *
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		Game zorkgame = new Game();
		zorkgame.play();
	}

}



