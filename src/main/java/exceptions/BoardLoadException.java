package exceptions;

public class BoardLoadException extends Exception {
    public BoardLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}