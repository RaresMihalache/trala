package com.example.raresm.sdproject1.exceptions;


public class UserIdExceptionResponse {
    private String userId;

    public UserIdExceptionResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}