package cn.com.bluemoon.lock.exception;

/**
 * 对于不可重入的锁将抛出此异常
 *
 * Created by LeonWong on 16/8/25.
 */
public class IllegalReentrantException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalReentrantException(Throwable cause) {
        super(cause);
    }

    public IllegalReentrantException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalReentrantException(String message) {
        super(message);
    }

    public IllegalReentrantException() {
        super();
    }
}