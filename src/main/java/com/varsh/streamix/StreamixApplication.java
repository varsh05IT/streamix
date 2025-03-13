package com.varsh.streamix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.varsh.streamix.model")
public class StreamixApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamixApplication.class, args);
	}

}
