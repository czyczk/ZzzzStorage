package dao;

/**
 * Created by czyczk on 2017-6-23.
 */
public class LibraryItemDaoException extends RuntimeException {
    public LibraryItemDaoException() {
    }

    public LibraryItemDaoException(String message) {
        super(message);
    }

    public LibraryItemDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryItemDaoException(Throwable cause) {
        super(cause);
    }

    public LibraryItemDaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
