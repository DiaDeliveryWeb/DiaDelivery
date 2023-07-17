package com.dia.delievery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DelieveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelieveryApplication.class, args);
    }

}
