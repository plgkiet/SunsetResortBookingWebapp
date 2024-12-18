package org.example.sunsetresortwebapp.Models;

public class CheckUserResponse {
    private String message;
    private boolean success;
    public CheckUserResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public CheckUserResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
