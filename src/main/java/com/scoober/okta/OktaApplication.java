package com.scoober.okta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OktaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OktaApplication.class, args);
    }
}
