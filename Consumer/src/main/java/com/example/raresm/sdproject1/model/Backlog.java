package com.example.raresm.sdproject1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "device_id", nullable = false)
    @JsonIgnore
    private Device device;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    private List<Consumption> deviceConsumptions = new ArrayList<>();

    public Backlog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Consumption> getDeviceConsumptions() {
        return deviceConsumptions;
    }

    public void setDeviceConsumptions(List<Consumption> deviceConsumptions) {
        this.deviceConsumptions = deviceConsumptions;
    }
}
