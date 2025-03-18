package exceptions;

public class PlayerLoadException extends Exception {
    public PlayerLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}