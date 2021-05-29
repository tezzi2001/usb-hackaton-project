package com.heroku.labshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LabshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabshareApplication.class, args);
    }

}
