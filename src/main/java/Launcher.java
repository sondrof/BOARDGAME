import app.MainApp;

/**
 * Entry point class for launching the board game application.
 * This class serves as a simple wrapper to start the main application.
 *
 * <p>The launcher:
 * <ul>
 *     <li>Provides a clean entry point for the application</li>
 *     <li>Delegates to the main application class</li>
 *     <li>Handles command line arguments</li>
 * </ul>
 *
 * @author Sondre Odberg
 * @version 1.0
 */
public class Launcher {
  /**
   * Main entry point for the application.
   * Delegates to the main application class to start the game.
   *
   * @param args Command line arguments passed to the application
   */
  public static void main(String[] args) {
    MainApp.main(args);
  }
}