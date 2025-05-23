package modell.exceptions;

/**
 * Exception thrown when there is an error saving player data to a file.
 * This typically occurs when there are I/O errors writing to the CSV file
 * or when the file location is not accessible.
 *
 * @author didrik
 * @version 1.0
 */
public class PlayerSaveException extends Exception {
  /**
   * Constructs a new PlayerSaveException with the specified detail message and cause.
   *
   * @param message the detail message (which is saved for later retrieval by the getMessage() method)
   * @param cause the cause (which is saved for later retrieval by the getCause() method)
   */
  public PlayerSaveException(String message, Throwable cause) {
    super(message, cause);
  }
}