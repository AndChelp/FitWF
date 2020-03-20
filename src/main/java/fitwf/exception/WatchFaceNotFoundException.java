package fitwf.exception;

public class WatchFaceNotFoundException extends RuntimeException {
    public WatchFaceNotFoundException(String message) {
        super(message);
    }
}
