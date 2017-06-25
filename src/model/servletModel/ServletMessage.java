package model.servletModel;

import model.IJsonable;

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
    public ServletMessage(String messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }
}
