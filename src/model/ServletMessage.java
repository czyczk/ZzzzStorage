package model;

/**
 * Created by czyczk on 2017-6-23.
 */
public class ServletMessage implements IJsonable {
    private String requestStatus;
    private String message;
    public String getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public ServletMessage() { }
    public ServletMessage(String requestStatus, String message) {
        this.requestStatus = requestStatus;
        this.message = message;
    }
}
