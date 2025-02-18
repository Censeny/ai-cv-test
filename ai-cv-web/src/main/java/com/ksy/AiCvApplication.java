package com.ksy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ksy"})
public class AiCvApplication {

    /**
     * @author csy
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(AiCvApplication.class,args);
    }
}
