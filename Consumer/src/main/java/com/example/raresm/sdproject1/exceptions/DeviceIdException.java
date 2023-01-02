package com.example.raresm.sdproject1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeviceIdException extends RuntimeException {

    public DeviceIdException(String message) {
        super(message);
    }
}
