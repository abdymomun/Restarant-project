package peaksoft.exception;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
