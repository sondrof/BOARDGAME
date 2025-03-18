package exceptions;

public class BoardSaveException extends Exception {
    public BoardSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}