package peaksoft.exception;

public class EmailBlankException extends RuntimeException {
    public EmailBlankException(String message) {
        super(message);
    }
}
