package fitwf.exception;

public class UsernameAndEmailAlreadyExistsException extends RuntimeException {
    public UsernameAndEmailAlreadyExistsException(String message) {
        super(message);
    }
}
