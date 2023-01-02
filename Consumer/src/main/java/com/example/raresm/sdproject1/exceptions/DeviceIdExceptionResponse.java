package com.example.raresm.sdproject1.exceptions;

public class DeviceIdExceptionResponse {
    private String deviceId;

    public DeviceIdExceptionResponse(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
