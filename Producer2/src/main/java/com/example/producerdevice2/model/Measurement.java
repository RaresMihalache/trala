package com.example.producerdevice2.model;

import java.io.Serializable;

public class Measurement implements Serializable {
    private String dateTime;
    private String deviceId;
    private String measValue;

    public Measurement() {
    }

    public Measurement(String dateTime, String deviceId, String measValue) {
        this.dateTime = dateTime;
        this.deviceId = deviceId;
        this.measValue = measValue;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMeasValue() {
        return measValue;
    }

    public void setMeasValue(String measValue) {
        this.measValue = measValue;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "dateTime='" + dateTime + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", measValue='" + measValue + '\'' +
                '}';
    }
}
