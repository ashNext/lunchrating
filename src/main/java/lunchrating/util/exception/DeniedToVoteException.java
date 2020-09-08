package lunchrating.util.exception;

public class DeniedToVoteException extends RuntimeException {
    public DeniedToVoteException(String msg) {
        super(msg);
    }
}
