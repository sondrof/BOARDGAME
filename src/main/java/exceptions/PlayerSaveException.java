package exceptions;

public class PlayerSaveException extends Exception {
    public PlayerSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}