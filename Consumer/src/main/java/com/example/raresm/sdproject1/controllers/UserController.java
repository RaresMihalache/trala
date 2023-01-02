package com.example.raresm.sdproject1.controllers;

import com.example.raresm.sdproject1.exceptions.MappingException;
import com.example.raresm.sdproject1.model.Consumption;
import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.model.User;
import com.example.raresm.sdproject1.payload.JwtLoginSuccessResponse;
import com.example.raresm.sdproject1.security.AuthenticationService;
import com.example.raresm.sdproject1.services.DeviceService;
import com.example.raresm.sdproject1.services.MapValidationErrorService;
import com.example.raresm.sdproject1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/{userId}/devices/all")
    public ResponseEntity<?> findAllDevicesByUserId(@PathVariable String userId){
        User user = userService.findByUserId(userId);

        return new ResponseEntity<List<Device>>(user.getDevices(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/devices/{deviceId}")
    public ResponseEntity<?> findDeviceByUserId(@PathVariable String userId, @PathVariable String deviceId){
        User user = userService.findByUserId(userId);
        Device device = deviceService.findByDeviceId(deviceId);

        for(Device device1 : user.getDevices()){
            if(device1.getId() == device.getId())
                return new ResponseEntity<Device>(device, HttpStatus.OK);
        }
        throw new MappingException("Invalid Mapping");
    }

    @GetMapping("/devices/{deviceId}")
    public ResponseEntity<?> findDeviceById(@PathVariable String deviceId){
        Device device = deviceService.findByDeviceId(deviceId);

        return new ResponseEntity<Device>(device, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        String response = authenticationService.login(user.getUsername(), user.getPassword());
        System.out.println(user.getUsername() + " " + user.getPassword());
        if(response == null)
            return new ResponseEntity<String>("Login failed", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<JwtLoginSuccessResponse>(new JwtLoginSuccessResponse(true, response), HttpStatus.OK);
    }

    @GetMapping("/deviceBoard/consumption/{deviceId}")
    public ResponseEntity<?> getConsumptionListByDeviceId(@PathVariable String deviceId) {
        List<Consumption> consumptions = deviceService.findByDeviceId(deviceId).getBacklog().getDeviceConsumptions();
        return new ResponseEntity<List<Consumption>>(consumptions, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/deviceBoard/consumption/{deviceId}/{date}")
    public ResponseEntity<?> getConsumptionListByDeviceIdAndDate(@PathVariable String deviceId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Consumption> consumptions = deviceService.findByDeviceId(deviceId).getBacklog().getDeviceConsumptions();
        for(Consumption consumption : consumptions)
            System.out.println(consumption.getDate().toString());
        System.out.println();
        System.out.println(date);
        consumptions.removeIf(
            consumption ->
                (consumption.getDate().getDay() != date.getDay()) ||
                (consumption.getDate().getMonth() != date.getMonth()) ||
                (consumption.getDate().getYear() != date.getYear())
            );
        return new ResponseEntity<List<Consumption>>(consumptions, HttpStatus.OK);
    }
}
