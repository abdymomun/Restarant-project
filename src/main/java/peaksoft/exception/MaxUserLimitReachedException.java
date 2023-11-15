package peaksoft.exception;

public class MaxUserLimitReachedException extends RuntimeException{
    public MaxUserLimitReachedException(String message) {
        super(message);
    }
}
