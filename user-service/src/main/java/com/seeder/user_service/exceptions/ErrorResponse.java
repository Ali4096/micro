package com.seeder.user_service.exceptions;

public class ErrorResponse {
    private int statusCode;
    private String message;

    // Constructor, getters and setters
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
