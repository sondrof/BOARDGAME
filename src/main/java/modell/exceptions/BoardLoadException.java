package modell.exceptions;

/**
 * Exception thrown when there is an error loading board configuration from a JSON file.
 * This can occur due to I/O errors, file not found, invalid JSON format,
 * or missing required fields in the board configuration.
 */
public class BoardLoadException extends Exception {
    /**
     * Constructs a new BoardLoadException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public BoardLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}