package com.aa.virtualroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.aa.virtualroom.model.FuntionDetails;
import com.aa.virtualroom.model.JobDetails;

@SpringBootApplication
@EnableConfigurationProperties({
	FuntionDetails.class
})
public class VirtualRoomApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualRoomApplication.class, args);
	}

}
