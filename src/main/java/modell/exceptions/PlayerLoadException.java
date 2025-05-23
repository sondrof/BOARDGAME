package modell.exceptions;

/**
 * Exception thrown when there is an error loading player data from a file.
 * This can occur due to I/O errors, file not found, or invalid data format
 * in the CSV file containing player information.
 *
 * @author didrik
 * @version 1.0
 */
public class PlayerLoadException extends Exception {
  /**
   * Constructs a new PlayerLoadException with the specified detail message and cause.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method)
   * @param cause the cause (which is saved for later retrieval by the getCause() method)
   */
  public PlayerLoadException(String message, Throwable cause) {
    super(message, cause);
  }
}