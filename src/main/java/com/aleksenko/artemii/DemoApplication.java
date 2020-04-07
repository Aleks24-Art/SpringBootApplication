package com.aleksenko.artemii;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    private final static Logger logger = Logger.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        logger.info("Starting the program in thread " + Thread.currentThread().getId());
        SpringApplication.run(DemoApplication.class, args);
    }
}

