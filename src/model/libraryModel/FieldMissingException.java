package model.libraryModel;

/**
 * Created by czyczk on 2017-6-25.
 */
public class FieldMissingException extends Exception {
    public FieldMissingException() {
    }

    public FieldMissingException(String message) {
        super(message);
    }

    public FieldMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldMissingException(Throwable cause) {
        super(cause);
    }

    public FieldMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
