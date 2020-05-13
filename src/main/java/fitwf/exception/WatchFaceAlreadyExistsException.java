package fitwf.exception;

public class WatchFaceAlreadyExistsException extends RuntimeException {
    public WatchFaceAlreadyExistsException(String message, int wfId) {
        super(message);
    }
}
