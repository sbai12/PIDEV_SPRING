package edu.esprit.payment.exeception;

public class ResourceNotFoundException extends RuntimeException {
    private final String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
