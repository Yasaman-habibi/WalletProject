package ir.freeland.springboot.exception;

public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValidationException(final String message) {
        super(message);
    }

    public ValidationException(final Throwable cause) {
        super(cause);
    }
    
}

