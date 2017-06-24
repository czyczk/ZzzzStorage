package model;

/**
 * Created by czyczk on 2017-6-23.
 */
public class ServletMessage implements IJsonable {
    private String messageType;
    private String message;
    public String getRequestStatus() {
        return messageType;
    }
    public void setRequestStatus(String requestStatus) {
        this.messageType = requestStatus;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ServletMessage() { }
    public ServletMessage(String requestStatus, String message) {
        this.messageType = requestStatus;
        this.message = message;
    }
}
