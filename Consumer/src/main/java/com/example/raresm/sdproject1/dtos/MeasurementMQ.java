package com.example.raresm.sdproject1.dtos;

import java.io.Serializable;

public class MeasurementMQ implements Serializable {
    private String dateTime;
    private String deviceId;
    private String measValue;

    public MeasurementMQ() {
    }

    public MeasurementMQ(String dateTime, String deviceId, String measValue) {
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
        return "MeasurementMQ{" +
                "dateTime='" + dateTime + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", measValue='" + measValue + '\'' +
                '}';
    }
}
