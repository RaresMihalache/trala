package com.example.raresm.sdproject1.controllers;


import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.model.User;
import com.example.raresm.sdproject1.services.DeviceService;
import com.example.raresm.sdproject1.services.MapValidationErrorService;
import com.example.raresm.sdproject1.services.UserService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mapping")
@CrossOrigin
public class MapperController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/{userId}/{deviceId}")
    public ResponseEntity<?> createNewMapping(@PathVariable String userId, @PathVariable String deviceId){
        User user = userService.findByUserId(userId);
        Device device = deviceService.findByDeviceId(deviceId);

        if(user != null && device != null){
            user.getDevices().add(device);
            device.setUser(user);
            deviceService.saveOrUpdateDevice(device);
            userService.saveOrUpdateUser(user);
        }

        return new ResponseEntity<String>("Mapping between User with Id '" + userId + "' and device with Id '" + deviceId + "' done successfully", HttpStatus.CREATED);
    }
}
