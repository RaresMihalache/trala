package com.example.raresm.sdproject1.controllers;

import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.services.DeviceService;
import com.example.raresm.sdproject1.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewDevice(@Valid @RequestBody Device device, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        Device device1 = deviceService.saveOrUpdateDevice(device);
        return new ResponseEntity<Device>(device, HttpStatus.CREATED);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable String deviceId){
        Device device = deviceService.findByDeviceId(deviceId);

        return new ResponseEntity<Device>(device, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Device> getAllDevices(){
        return deviceService.findAllDevices();
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable String deviceId){
        deviceService.deleteDeviceById(deviceId);

        return new ResponseEntity<String>("Device with Id '" + deviceId + "' was deleted.", HttpStatus.OK);
    }

}
