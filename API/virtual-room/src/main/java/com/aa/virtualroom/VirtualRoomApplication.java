package com.aa.virtualroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.aa.virtualroom.model.FunctionDetails;

@SpringBootApplication
@EnableConfigurationProperties({
    FunctionDetails.class
})

public class VirtualRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualRoomApplication.class, args);
    }

}
