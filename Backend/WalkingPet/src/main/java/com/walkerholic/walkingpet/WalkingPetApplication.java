package com.walkerholic.walkingpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WalkingPetApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalkingPetApplication.class, args);
    }

}
