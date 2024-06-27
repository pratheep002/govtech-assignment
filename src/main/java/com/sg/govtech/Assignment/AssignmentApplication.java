package com.sg.govtech.Assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AssignmentApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
		LOGGER.info("AssignmentApplication begins..");
	}

}
