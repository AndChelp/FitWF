package fitwf.exception;

public class UsernameAndEmailAlreadyExists extends RuntimeException {
    public UsernameAndEmailAlreadyExists(String message) {
        super(message);
    }
}
