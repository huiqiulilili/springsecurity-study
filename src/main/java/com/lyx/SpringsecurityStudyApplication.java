package com.lyx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.lyx.mapper")
public class SpringsecurityStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityStudyApplication.class, args);
    }

}
