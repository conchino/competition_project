package com.competition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class CompetitionProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompetitionProjectApplication.class, args);
    }
}
