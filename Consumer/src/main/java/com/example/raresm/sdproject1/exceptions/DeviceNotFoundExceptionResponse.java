package com.example.raresm.sdproject1.exceptions;

public class DeviceNotFoundExceptionResponse {

    private String ProjectNotFound;

    public DeviceNotFoundExceptionResponse(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }

    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    public void setProjectNotFound(String projectNotFound) {
        ProjectNotFound = projectNotFound;
    }
}
