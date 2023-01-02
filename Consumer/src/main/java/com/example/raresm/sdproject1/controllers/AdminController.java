package com.example.raresm.sdproject1.controllers;

import com.example.raresm.sdproject1.model.Consumption;
import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.model.User;
import com.example.raresm.sdproject1.services.ConsumptionService;
import com.example.raresm.sdproject1.services.DeviceService;
import com.example.raresm.sdproject1.services.MapValidationErrorService;
import com.example.raresm.sdproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Users APIs **/

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/all")
    public Iterable<User> getAllUsers(Authentication authentication){
        System.out.println(authentication.getAuthorities());
        return userService.findAllUsers();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId){
        User user = userService.findByUserId(userId);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/users/{userId}/devices")
    public ResponseEntity<?> findAllDevicesByUserId(@PathVariable String userId){
        User user = userService.findByUserId(userId);

        return new ResponseEntity<List<Device>>(user.getDevices(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User user, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        User user1 = userService.saveOrUpdateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        userService.deleteUserById(userId);

        return new ResponseEntity<String>("User with Id '" + userId +"' was deleted.", HttpStatus.OK);
    }


    /** Devices APIs **/

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/devices/all")
    public Iterable<Device> getAllDevices(){
        return deviceService.findAllDevices();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable String deviceId){
        Device device = deviceService.findByDeviceId(deviceId);

        return new ResponseEntity<Device>(device, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/devices")
    public ResponseEntity<?> createNewDevice(@Valid @RequestBody Device device, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)
            return errorMap;

        Device device1 = deviceService.saveOrUpdateDevice(device);
        return new ResponseEntity<Device>(device, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @DeleteMapping("/devices/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable String deviceId){
        deviceService.deleteDeviceById(deviceId);

        return new ResponseEntity<String>("Device with Id '" + deviceId + "' was deleted.", HttpStatus.OK);
    }

    /** Mapping APIs **/

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/mappings/{userId}/{deviceId}")
    public ResponseEntity<?> createNewMapping(@PathVariable String userId, @PathVariable String deviceId){
        System.out.println("abc");
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

    /** Consumption APIs **/

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/backlog/{backlog_id}")
    public ResponseEntity<?> addConsumptionToBacklog(@Valid @RequestBody Consumption consumption,
                                                     BindingResult result, @PathVariable Integer backlog_id){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Consumption consumption1 = consumptionService.addConsumption(backlog_id, consumption);

        return new ResponseEntity<Consumption>(consumption1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @GetMapping("/backlog/{backlog_id}")
    public ResponseEntity<List<Consumption>> getDeviceBacklog(@PathVariable Integer backlog_id){
        return new ResponseEntity<List<Consumption>>(consumptionService.findBacklogById(backlog_id), HttpStatus.OK);
    }

    @GetMapping("/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> getConsumption(@PathVariable Integer backlog_id, @PathVariable Integer consumption_id){
        Optional<Consumption> consumption = consumptionService.findConsumptionById(backlog_id, consumption_id);

        return new ResponseEntity<Optional<Consumption>>(consumption, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> updateConsumption(@Valid @RequestBody Consumption consumption, BindingResult result,
                                               @PathVariable Integer backlog_id, @PathVariable Integer consumption_id){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Consumption updatedConsumption = consumptionService.updateConsumptionById(consumption, backlog_id, consumption_id);

        return new ResponseEntity<Consumption>(updatedConsumption, HttpStatus.OK);
    }

    @DeleteMapping("/backlog/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> deleteConsumption(@PathVariable Integer backlog_id, @PathVariable Integer consumption_id){
        consumptionService.deleteConsumptionById(backlog_id, consumption_id);

        return new ResponseEntity<String>("Consumption '" + consumption_id +"' was deleted successfully", HttpStatus.OK);
    }

}
