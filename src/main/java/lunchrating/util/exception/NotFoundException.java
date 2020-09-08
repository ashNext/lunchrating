package lunchrating.util.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(int id) {
        super("id=" + id);
    }
}
