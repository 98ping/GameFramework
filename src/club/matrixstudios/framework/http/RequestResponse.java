package club.matrixstudios.framework.http;


import okhttp3.Request;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RequestResponse {
    private boolean successful;

    private String errorMessage;

    private String response;

    Request.Builder requestBuilder;

    public RequestResponse(boolean successful, String errorMessage, String response, Request.Builder requestBuilder) {
        this.successful = successful;
        this.errorMessage = errorMessage;
        this.response = response;
        this.requestBuilder = requestBuilder;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getResponse() {
        return this.response;
    }

    public Request.Builder getRequestBuilder() {
        return this.requestBuilder;
    }

    public boolean wasSuccessful() {
        return this.successful;
    }

    public JSONObject asJSONObject() {
        return new JSONObject(new JSONTokener(this.response));
    }

    public Request rebuildRequest() {
        return this.requestBuilder.build();
    }

    public boolean couldNotConnect() {
        return (getErrorMessage() != null && getErrorMessage().toLowerCase().contains("could not connect to api"));
    }
}
