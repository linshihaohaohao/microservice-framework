package cn.com.bluemoon.lock.exception;

/**
 * Created by leonwong on 2016/12/21.
 */
public class LockException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public LockException() {
        super();
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockException(Throwable cause) {
        super(cause);
    }

    protected LockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
