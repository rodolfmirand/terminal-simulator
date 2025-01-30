package TerminalSimulator.models.dto.response;

public class Response {

    private String message;

    private String path;

    public Response(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
