package com.example.raresm.sdproject1.services;

import com.example.raresm.sdproject1.exceptions.DeviceIdException;
import com.example.raresm.sdproject1.model.Backlog;
import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.repos.BacklogRepository;
import com.example.raresm.sdproject1.repos.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Device saveOrUpdateDevice(Device device){
        // Logic

        if(device.getId() == null){
            Backlog backlog = new Backlog();
            device.setBacklog(backlog);
            backlog.setDevice(device);
        }
        else {
            device.setBacklog(backlogRepository.findByDevice_Id(device.getId()));
        }

        return deviceRepository.save(device);
    }

    public Device findByDeviceId(String deviceId){
        Integer deviceIdLong = Integer.parseInt(deviceId);
        Device device = deviceRepository.findDeviceById(deviceIdLong);
        if(device == null){
            throw new DeviceIdException("Device Id: '" + deviceId + "' does not exist");
        }

        return device;
    }

    public Iterable<Device> findAllDevices(){
        return deviceRepository.findAll();
    }

    public void deleteDeviceById(String deviceId){
        Integer deviceIdLong = Integer.parseInt(deviceId);
        Device device = deviceRepository.findDeviceById(deviceIdLong);
        if(device == null){
            throw new DeviceIdException("Cannot delete Device with ID '" + deviceId + "'. This device ID does not exist.");
        }

        deviceRepository.delete(device);
    }
}
