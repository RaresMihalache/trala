package com.example.raresm.sdproject1.security;


import org.springframework.stereotype.Service;


public interface AuthenticationService {
    public String login(String username, String password);
}
