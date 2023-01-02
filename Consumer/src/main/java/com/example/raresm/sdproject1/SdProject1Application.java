package com.example.raresm.sdproject1;

import com.example.raresm.sdproject1.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SdProject1Application {

    public static void main(String[] args) {
        SpringApplication.run(SdProject1Application.class, args);
    }

}
