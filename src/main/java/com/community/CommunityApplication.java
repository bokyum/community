package com.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CommunityApplication {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
