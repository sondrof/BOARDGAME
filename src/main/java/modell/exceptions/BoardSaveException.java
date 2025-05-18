package modell.exceptions;

/**
 * Exception thrown when there is an error saving board configuration to a JSON file.
 * This typically occurs when there are I/O errors writing to the file,
 * when the file location is not accessible, or when there are issues with JSON serialization.
 */
public class BoardSaveException extends Exception {
    /**
     * Constructs a new BoardSaveException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public BoardSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}