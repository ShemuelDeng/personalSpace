package com.shemuel.site;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shemuel.site.mapper")
public class PersonalSiteJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalSiteJavaApplication.class, args);
    }

}
